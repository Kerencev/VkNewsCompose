package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.views.HomeTabLayout
import com.kerencev.vknewscompose.presentation.screens.news.NewsParams
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen
import com.kerencev.vknewscompose.presentation.screens.news.NewsType
import com.kerencev.vknewscompose.presentation.screens.news.NewsViewModel
import com.kerencev.vknewscompose.presentation.screens.news.RecommendationViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
) {
    val component = getApplicationComponent()
        .getNewsScreenComponentFactory()
        .create(params = NewsParams(type = NewsType.NEWS))
    val newsViewModel: NewsViewModel =
        viewModel(factory = component.getViewModelFactory())
    val recommendationViewModel: RecommendationViewModel =
        viewModel(factory = component.getViewModelFactory())

    HomeScreenContent(
        newsViewModel = newsViewModel,
        recommendationViewModel = recommendationViewModel,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        onError = onError,
        onImageClick = onImageClick,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    newsViewModel: NewsViewModel,
    recommendationViewModel: RecommendationViewModel,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { NewsType.values().size })

    Column(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
        HomeTabLayout(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(top = 28.dp),
            pagerState = pagerState
        )
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                NewsType.NEWS.index -> {
                    NewsScreen(
                        viewModel = newsViewModel,
                        onCommentsClick = onCommentsClick,
                        onError = onError,
                        onImageClick = onImageClick,
                    )
                }

                NewsType.RECOMMENDATION.index -> {
                    NewsScreen(
                        viewModel = recommendationViewModel,
                        onCommentsClick = onCommentsClick,
                        onError = onError,
                        onImageClick = onImageClick,
                    )
                }
            }
        }
    }
}
