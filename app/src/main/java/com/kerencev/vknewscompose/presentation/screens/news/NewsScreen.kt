package com.kerencev.vknewscompose.presentation.screens.news

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.NewsType
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.common.views.TopPopupCard
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEvent
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsShot
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsViewState
import com.kerencev.vknewscompose.presentation.screens.news.views.NewsCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NewsScreen(
    params: NewsParams,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
) {
    val component = getApplicationComponent()
        .getNewsScreenComponentFactory()
        .create(params = params)
    val viewModel: NewsViewModel = viewModel(factory = component.getViewModelFactory())
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(NewsShot.None)
    val sendEvent: (NewsEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    NewsScreenContent(
        toolbarTitle = getToolbarTitleByType(newsType = params.type),
        state = state,
        shot = shot,
        coroutineScope = coroutineScope,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        sendEvent = sendEvent,
        onError = onError,
        onImageClick = onImageClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenContent(
    toolbarTitle: String,
    state: State<NewsViewState>,
    shot: State<NewsShot>,
    coroutineScope: CoroutineScope,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    sendEvent: (NewsEvent) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        paddingValues = paddingValues,
        toolBarTitle = {
            Text(
                text = toolbarTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.background_news)),
                contentAlignment = Alignment.Center
            ) {
                val currentState = state.value

                SwipeRefresh(
                    state = SwipeRefreshState(isRefreshing = currentState.isSwipeRefreshing),
                    onRefresh = { sendEvent(NewsEvent.GetNews(isRefresh = true)) },
                    indicatorPadding = PaddingValues(top = innerPadding.calculateTopPadding()),
                ) {
                    val listState = rememberLazyListState()

                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(top = innerPadding.calculateTopPadding() + 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(currentState.newsList, { it.id }) { newsItem ->
                            NewsCard(
                                newsModel = newsItem,
                                onCommentsClick = onCommentsClick,
                                onLikesClick = { sendEvent(NewsEvent.ChangeLikeStatus(newsItem)) },
                                onImageClick = { index ->
                                    onImageClick(index, newsItem.id)
                                }
                            )
                        }
                        item {
                            when {
                                currentState.isLoading -> ProgressBarDefault(
                                    modifier = Modifier.padding(16.dp)
                                )

                                currentState.isError -> TextWithButton(
                                    modifier = Modifier.padding(16.dp),
                                    title = stringResource(id = R.string.load_data_error),
                                    onClick = { sendEvent(NewsEvent.GetNews()) }
                                )

                                else -> SideEffect { sendEvent(NewsEvent.GetNews()) }
                            }
                        }
                    }

                    LaunchedEffect(listState) {
                        snapshotFlow { listState.firstVisibleItemIndex }
                            .distinctUntilChanged()
                            .onEach { sendEvent(NewsEvent.OnUserScroll(it)) }
                            .flowOn(Dispatchers.IO)
                            .launchIn(coroutineScope)
                    }

                    LaunchedEffect(key1 = currentState.scrollToTop) {
                        if (currentState.scrollToTop) {
                            listState.scrollToItem(0)
                            sendEvent(NewsEvent.OnScrollToTop)
                        }
                    }
                }

                val visibilityState by animateFloatAsState(
                    targetValue = if (currentState.isScrollToTopVisible) 1f else 0f
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    TopPopupCard(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding() + 8.dp)
                            .alpha(visibilityState),
                        text = stringResource(id = R.string.new_posts),
                        iconRes = R.drawable.ic_arrow_up,
                        onClick = {
                            if (currentState.isScrollToTopVisible)
                                sendEvent(NewsEvent.GetNews(isRefresh = true))
                        }
                    )
                }
            }

            when (val currentShot = shot.value) {
                is NewsShot.ShowErrorMessage -> {
                    onError(currentShot.message)
                    sendEvent(NewsEvent.OnErrorInvoked)
                }

                is NewsShot.None -> Unit
            }
        }
    )
}

@Composable
private fun getToolbarTitleByType(newsType: NewsType): String {
    return stringResource(
        id = when (newsType) {
            NewsType.BY_DATE -> R.string.news
            NewsType.RECOMMENDATION -> R.string.recommendation
        }
    )
}
