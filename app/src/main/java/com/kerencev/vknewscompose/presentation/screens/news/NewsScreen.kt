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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.views.loading.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.text.TextWithButton
import com.kerencev.vknewscompose.presentation.common.views.text.TopPopupCard
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEvent
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsShot
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsViewState
import com.kerencev.vknewscompose.presentation.screens.news.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun NewsScreen(
    viewModel: BaseViewModel<NewsEvent, NewsViewState, NewsShot>,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    showSnackBar: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
    onHeaderClick: (params: ProfileParams) -> Unit,
    paddingValues: PaddingValues = PaddingValues(),
) {
    val noFeatureMessage = stringResource(id = R.string.no_feature_message)
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(NewsShot.None)
    val sendEvent: (NewsEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_news)),
        contentAlignment = Alignment.Center
    ) {
        val currentState = state.value
        val listState = rememberLazyListState()

        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .onEach { sendEvent(NewsEvent.OnUserScroll(it)) }
                .flowOn(Dispatchers.IO)
                .launchIn(coroutineScope)
        }

        SwipeRefresh(
            state = SwipeRefreshState(isRefreshing = currentState.isSwipeRefreshing),
            onRefresh = { sendEvent(NewsEvent.GetNews(isRefresh = true)) },
        ) {

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = currentState.newsList,
                    key = { it.id }
                ) { newsItem ->
                    NewsCard(
                        newsModel = newsItem,
                        onCommentsClick = { onCommentsClick(newsItem) },
                        onLikesClick = {
                            sendEvent(NewsEvent.ChangeLikeStatus(newsModelUi = newsItem))
                        },
                        onImageClick = { index ->
                            onImageClick(index, newsItem.id)
                        },
                        onHeaderClick = {
                            onHeaderClick(
                                ProfileParams(id = newsItem.ownerId, type = newsItem.type)
                            )
                        },
                        onIconMoreClick = { showSnackBar(noFeatureMessage) },
                        onShareClick = { showSnackBar(noFeatureMessage) },
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
                    .padding(top = 8.dp)
                    .alpha(visibilityState),
                text = stringResource(id = R.string.new_posts),
                iconRes = R.drawable.ic_arrow_up,
                onClick = {
                    if (currentState.isScrollToTopVisible)
                        sendEvent(NewsEvent.GetNews(isRefresh = true))
                }
            )
        }

        when (val currentShot = shot.value) {
            is NewsShot.ShowErrorMessage -> {
                showSnackBar(currentShot.message)
                sendEvent(NewsEvent.OnErrorInvoked)
            }

            is NewsShot.ScrollToTop -> {
                SideEffect {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                        sendEvent(NewsEvent.OnScrollToTop)
                    }
                }
            }

            is NewsShot.None -> Unit
        }
    }

}
