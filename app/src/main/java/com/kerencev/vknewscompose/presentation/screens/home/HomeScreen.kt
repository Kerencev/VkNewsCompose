package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnit
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.SnackBarWithAction
import com.kerencev.vknewscompose.presentation.common.views.SwipeWithBackground
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEvent
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeShot
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard

@Composable
fun HomeScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(HomeShot.None)

    val loadNews = rememberUnit { viewModel.send(HomeEvent.GetNews) }
    val onLikeClick: (NewsModelUi) -> Unit =
        rememberUnitParams { viewModel.send(HomeEvent.ChangeLikeStatus(it)) }
    val onItemDismiss: (NewsModelUi) -> Unit =
        rememberUnitParams { viewModel.send(HomeEvent.DeleteNews(it)) }
    val hideSnackBar = rememberUnit { viewModel.send(HomeEvent.HideSnackBar) }

    HomeScreenContent(
        state = state,
        shot = shot,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        onItemDismiss = onItemDismiss,
        onLikeClick = onLikeClick,
        loadNews = loadNews,
        onSnackBarClick = hideSnackBar
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    state: State<HomeViewState>,
    shot: State<HomeShot>,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onItemDismiss: (newsModel: NewsModelUi) -> Unit,
    onLikeClick: (newsModel: NewsModelUi) -> Unit,
    loadNews: () -> Unit,
    onSnackBarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_news)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.value.newsList, { it.id }) { newsItem ->
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
                    state.value.isLoading -> ProgressBarDefault(modifier = Modifier.padding(16.dp))

                    state.value.isError -> TextWithButton(
                        modifier = Modifier.padding(16.dp),
                        title = stringResource(id = R.string.load_data_error),
                        onRetryClick = loadNews
                    )

                    else -> SideEffect { loadNews() }
                }
            }
        }
    }

    when (val currentShot = shot.value) {
        is HomeShot.ShowErrorMessage -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                SnackBarWithAction(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 64.dp),
                    text = stringResource(id = R.string.set_error_cause, currentShot.message),
                    actionLabel = stringResource(id = R.string.ok),
                    onClick = onSnackBarClick
                )
            }
        }

        is HomeShot.None -> Unit
    }
}
