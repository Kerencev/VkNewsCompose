package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen
import com.kerencev.vknewscompose.ui.theme.DarkBlue

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial).value

    when (screenState) {
        is HomeScreenState.Initial -> {

        }
        HomeScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
        is HomeScreenState.Home -> {
            NewsScreen(
                viewModel = viewModel,
                news = screenState.news,
                paddingValues = paddingValues,
                onCommentsClick = onCommentsClick,
                nextDataIsLoading = screenState.nextDataIsLoading
            )
        }
    }
}
