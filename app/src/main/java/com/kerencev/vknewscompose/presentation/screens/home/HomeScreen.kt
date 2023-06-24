package com.kerencev.vknewscompose.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.common.compose.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.news.NewsScreen

@Composable
fun HomeScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(ScreenState.Loading)
    HomeScreenContent(
        viewModel = viewModel,
        state = screenState,
        paddingValues = paddingValues,
        onCommentsClick = onCommentsClick
    )
}

@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel,
    state: State<ScreenState<Home>>,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit
) {
    when (val currentState = state.value) {
        is ScreenState.Loading -> ProgressBarDefault()

        is ScreenState.Content -> {
            NewsScreen(
                viewModel = viewModel,
                news = currentState.data.news,
                paddingValues = paddingValues,
                onCommentsClick = onCommentsClick,
                nextDataIsLoading = currentState.data.nextDataIsLoading
            )
        }

        is ScreenState.Empty -> Unit

        is ScreenState.Error -> Unit
    }
}
