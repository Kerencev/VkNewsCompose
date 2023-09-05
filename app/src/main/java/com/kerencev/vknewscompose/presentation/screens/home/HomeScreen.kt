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
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.views.tabs.HomeTab
import com.kerencev.vknewscompose.presentation.screens.home.views.tabs.HomeTabLayout
import com.kerencev.vknewscompose.presentation.screens.news.NewsParams
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen
import com.kerencev.vknewscompose.presentation.screens.news.NewsType
import com.kerencev.vknewscompose.presentation.screens.news.NewsViewModel
import com.kerencev.vknewscompose.presentation.screens.news.RecommendationViewModel
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.suggested.SuggestedScreen

@Composable
fun HomeScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
    onHeaderClick: (params: ProfileParams) -> Unit,
    onSuggestedClick: (params: ProfileParams) -> Unit,
) {
    val component = getApplicationComponent()
        .getNewsScreenComponentFactory()
        .create(params = NewsParams(type = NewsType.NEWS))
    val newsViewModel: NewsViewModel =
        viewModel(factory = component.getViewModelFactory())
    val recommendationViewModel: RecommendationViewModel =
        viewModel(factory = component.getViewModelFactory())

    HomeScreenContent(
        viewModelFactory = viewModelFactory,
        newsViewModel = newsViewModel,
        recommendationViewModel = recommendationViewModel,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick,
        onError = onError,
        onImageClick = onImageClick,
        onHeaderClick = onHeaderClick,
        onSuggestedClick = onSuggestedClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    viewModelFactory: ViewModelFactory,
    newsViewModel: NewsViewModel,
    recommendationViewModel: RecommendationViewModel,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onError: (message: String) -> Unit,
    onImageClick: (index: Int, newsModelId: Long) -> Unit,
    onHeaderClick: (params: ProfileParams) -> Unit,
    onSuggestedClick: (params: ProfileParams) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { HomeTab.values().size })

    Column(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
        HomeTabLayout(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(top = 28.dp),
            pagerState = pagerState
        )
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                HomeTab.NEWS.index -> {
                    NewsScreen(
                        viewModel = newsViewModel,
                        onCommentsClick = onCommentsClick,
                        onError = onError,
                        onImageClick = onImageClick,
                        onHeaderClick = onHeaderClick,
                    )
                }

                HomeTab.RECOMMENDATION.index -> {
                    NewsScreen(
                        viewModel = recommendationViewModel,
                        onCommentsClick = onCommentsClick,
                        onError = onError,
                        onImageClick = onImageClick,
                        onHeaderClick = onHeaderClick,
                    )
                }

                HomeTab.SUGGESTED.index -> {
                    SuggestedScreen(
                        viewModelFactory = viewModelFactory,
                        onItemClick = onSuggestedClick,
                    )
                }
            }
        }
    }
}
