package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.domain.model.NewsModel
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState.value) {
        is HomeScreenState.Initial -> {

        }
        is HomeScreenState.Home -> {
            NewsScreen(
                viewModel = viewModel,
                news = currentState.news,
                paddingValues = paddingValues,
                onCommentsClick = onCommentsClick
            )
        }
    }
}
