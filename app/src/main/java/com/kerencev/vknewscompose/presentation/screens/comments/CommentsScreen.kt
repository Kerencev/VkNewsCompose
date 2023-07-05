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
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnit
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsEvent
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsViewState
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
    val state = viewModel.observedState.collectAsState()

    val onRetryClick = rememberUnit { viewModel.send(CommentsEvent.GetComments(newsModel)) }

    CommentsScreenContent(
        state = state,
        newsModel = newsModel,
        onBackPressed = onBackPressed,
        onRetryClick = onRetryClick
    )
}

@Composable
fun CommentsScreenContent(
    state: State<CommentsViewState>,
    newsModel: NewsModelUi,
    onBackPressed: () -> Unit,
    onRetryClick: () -> Unit
) {
    ScaffoldWithToolbar(
        title = newsModel.communityName,
        subTitle = stringResource(id = R.string.comments),
        onBackPressed = { onBackPressed() }
    ) { paddingValues ->
        val currentState = state.value

        when {
            currentState.isLoading -> ProgressBarDefault(modifier = Modifier.fillMaxSize())

            currentState.isError -> TextWithButton(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.something_went_wrong),
                onClick = onRetryClick
            )

            currentState.commentsList.isEmpty() -> TextWithButton(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.empty_comments),
                onClick = onRetryClick
            )
        }

        CommentsColumn(
            comments = currentState.commentsList,
            paddingValues = paddingValues
        )
    }
}
