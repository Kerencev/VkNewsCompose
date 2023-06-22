package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.common.compose.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val screenState = viewModel.screenState.collectAsState(ScreenState.Loading).value

    when (screenState) {

        is ScreenState.Loading -> ProgressBarDefault()

        is ScreenState.Content -> {
            NewsScreen(
                viewModel = viewModel,
                news = screenState.data.news,
                paddingValues = paddingValues,
                onCommentsClick = onCommentsClick,
                nextDataIsLoading = screenState.data.nextDataIsLoading
            )
        }

        is ScreenState.Empty -> Unit

        is ScreenState.Error -> Unit
    }
}
