package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.app.getApplicationComponent
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnit
import com.kerencev.vknewscompose.presentation.common.views.loading.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.toolbar.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.text.TextWithButton
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsEvent
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsViewState
import com.kerencev.vknewscompose.presentation.screens.comments.views.CommentsColumn
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun CommentsScreen(
    newsModel: NewsModelUi,
    onBackPressed: () -> Unit,
    onCommentClick: (params: ProfileParams) -> Unit,
) {
    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(newsModel)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.observedState.collectAsState()

    val onRetryClick = rememberUnit { viewModel.send(CommentsEvent.GetComments(newsModel)) }

    CommentsScreenContent(
        state = state,
        onBackPressed = onBackPressed,
        onRetryClick = onRetryClick,
        onCommentClick = onCommentClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreenContent(
    state: State<CommentsViewState>,
    onBackPressed: () -> Unit,
    onRetryClick: () -> Unit,
    onCommentClick: (params: ProfileParams) -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        toolBarTitle = {
            Text(
                text = stringResource(id = R.string.comments),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        toolBarNavigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = LightBlue
                )
            }
        }
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
            paddingValues = paddingValues,
            onCommentClick = onCommentClick
        )
    }
}
