package com.kerencev.vknewscompose.presentation.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.kerencev.vknewscompose.presentation.main.MainViewModel
import com.kerencev.vknewscompose.presentation.screens.comments.CommentsScreen
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState.value) {
        is HomeScreenState.Initial -> {

        }
        is HomeScreenState.News -> {
            NewsScreen(
                viewModel = viewModel,
                news = currentState.news,
                paddingValues = paddingValues
            )
        }
        is HomeScreenState.Comments -> {
            CommentsScreen(
                newsModel = currentState.newsModel,
                comments = currentState.comments,
                onBackPressed = { viewModel.onBackPressedComments() }
            )
            BackHandler { viewModel.onBackPressedComments() }
        }
    }
}
