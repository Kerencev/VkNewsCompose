package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnit
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.SnackBarWithAction
import com.kerencev.vknewscompose.presentation.common.views.SwipeWithBackground
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.common.views.TopPopupCard
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEvent
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeShot
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(HomeShot.None)

    val loadNews: (Boolean) -> Unit = rememberUnitParams { viewModel.send(HomeEvent.GetNews(it)) }
    val onLikeClick: (NewsModelUi) -> Unit =
        rememberUnitParams { viewModel.send(HomeEvent.ChangeLikeStatus(it)) }
    val onItemDismiss: (NewsModelUi) -> Unit =
        rememberUnitParams { viewModel.send(HomeEvent.DeleteNews(it)) }
    val hideSnackBar = rememberUnit { viewModel.send(HomeEvent.HideSnackBar) }
    val onScrollToTop = rememberUnit { viewModel.send(HomeEvent.OnScrollToTop) }
    val onUserScroll: (Int) -> Unit =
        rememberUnitParams { viewModel.send(HomeEvent.OnUserScroll(it)) }

    HomeScreenContent(
        state = state,
        shot = shot,
        coroutineScope = coroutineScope,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        onItemDismiss = onItemDismiss,
        onLikeClick = onLikeClick,
        loadNews = loadNews,
        onSnackBarClick = hideSnackBar,
        onScrollToTop = onScrollToTop,
        onUserScroll = onUserScroll
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: State<HomeViewState>,
    shot: State<HomeShot>,
    coroutineScope: CoroutineScope,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onItemDismiss: (newsModel: NewsModelUi) -> Unit,
    onLikeClick: (newsModel: NewsModelUi) -> Unit,
    loadNews: (isRefresh: Boolean) -> Unit,
    onSnackBarClick: () -> Unit,
    onScrollToTop: () -> Unit,
    onUserScroll: (Int) -> Unit
) {
    ScaffoldWithCollapsingToolbar(
        paddingValues = paddingValues,
        toolBarTitle = {
            Text(
                text = stringResource(id = R.string.news),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.background_news)),
                contentAlignment = Alignment.Center
            ) {
                val currentState = state.value

                SwipeRefresh(
                    state = SwipeRefreshState(isRefreshing = currentState.isSwipeRefreshing),
                    onRefresh = { loadNews(true) },
                ) {
                    val listState = rememberLazyListState()

                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(currentState.newsList, { it.id }) { newsItem ->
                            SwipeWithBackground(
                                modifier = Modifier.animateItemPlacement(),
                                onDismiss = { onItemDismiss(newsItem) },
                                backgroundColor = Color.Red,
                                backgroundText = stringResource(id = R.string.delete),
                                backgroundTextColor = Color.White
                            ) {
                                NewsCard(newsModel = newsItem,
                                    onCommentsClick = onCommentsClick,
                                    onLikesClick = { onLikeClick(newsItem) })
                            }
                        }
                        item {
                            when {
                                currentState.isLoading -> ProgressBarDefault(
                                    modifier = Modifier.padding(
                                        16.dp
                                    )
                                )

                                currentState.isError -> TextWithButton(
                                    modifier = Modifier.padding(16.dp),
                                    title = stringResource(id = R.string.load_data_error),
                                    onClick = { loadNews(false) }
                                )

                                else -> SideEffect { loadNews(false) }
                            }
                        }
                    }

                    LaunchedEffect(listState) {
                        snapshotFlow { listState.firstVisibleItemIndex }
                            .distinctUntilChanged()
                            .onEach {
                                onUserScroll(it)
                            }
                            .flowOn(Dispatchers.IO)
                            .launchIn(coroutineScope)
                    }

                    LaunchedEffect(key1 = currentState.scrollToTop) {
                        if (currentState.scrollToTop) {
                            listState.scrollToItem(0)
                            onScrollToTop()
                        }
                    }
                }

                val visibilityState by animateFloatAsState(targetValue = if (currentState.isScrollToTopVisible) 1f else 0f)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    TopPopupCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .alpha(visibilityState),
                        text = stringResource(id = R.string.new_posts),
                        iconRes = R.drawable.ic_arrow_up,
                        onClick = { if (currentState.isScrollToTopVisible) loadNews(true) }
                    )
                }
            }

            when (val currentShot = shot.value) {
                is HomeShot.ShowErrorMessage -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        SnackBarWithAction(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 64.dp),
                            text = stringResource(
                                id = R.string.set_error_cause,
                                currentShot.message
                            ),
                            actionLabel = stringResource(id = R.string.ok),
                            onClick = onSnackBarClick
                        )
                    }
                }

                is HomeShot.None -> Unit
            }
        }
    )
}
