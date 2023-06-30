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
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard

@Composable
fun HomeScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(HomeState())
    HomeScreenContent(
        state = screenState,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        onItemDismiss = viewModel::onNewsItemDismiss,
        onLikeClick = viewModel::changeLikesStatus,
        loadNews = viewModel::loadNews
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    state: State<HomeState>,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onItemDismiss: (newsModel: NewsModelUi) -> Unit,
    onLikeClick: (newsModel: NewsModelUi) -> Unit,
    loadNews: () -> Unit
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
            items(state.value.items, { it.id }) { newsItem ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    onItemDismiss(newsItem)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    directions = setOf(DismissDirection.EndToStart),
                    state = dismissState,
                    background = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                                .alpha(0.5f)
                                .background(Color.Red),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(id = R.string.delete),
                                fontSize = 24.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.White
                            )
                        }
                    }
                ) {
                    NewsCard(
                        newsModel = newsItem,
                        onCommentsClick = onCommentsClick,
                        onLikesClick = { onLikeClick(newsItem) }
                    )
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
}
