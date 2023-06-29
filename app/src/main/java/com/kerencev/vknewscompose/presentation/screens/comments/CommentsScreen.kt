package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.comments.views.CommentsColumn

@Composable
fun CommentsScreen(
    newsModel: NewsModelUi,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(newsModel)
    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.screenState.collectAsState(ScreenState.Loading)
    CommentsScreenContent(
        state = state,
        viewModel = viewModel,
        newsModel = newsModel,
        onBackPressed = onBackPressed
    )
}

@Composable
fun CommentsScreenContent(
    state: State<ScreenState<List<CommentModel>>>,
    viewModel: CommentsViewModel,
    newsModel: NewsModelUi,
    onBackPressed: () -> Unit
) {
    ScaffoldWithToolbar(
        title = newsModel.communityName,
        subTitle = stringResource(id = R.string.comments),
        onBackPressed = { onBackPressed() }
    ) { paddingValues ->
        when (val currentState = state.value) {
            is ScreenState.Empty -> TextWithButton(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.empty_comments),
                onRetryClick = viewModel::loadData
            )

            is ScreenState.Loading -> ProgressBarDefault(modifier = Modifier.fillMaxSize())

            is ScreenState.Content -> CommentsColumn(
                comments = currentState.data,
                paddingValues = paddingValues
            )

            is ScreenState.Error -> TextWithButton(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.something_went_wrong),
                onRetryClick = viewModel::loadData
            )
        }
    }
}
