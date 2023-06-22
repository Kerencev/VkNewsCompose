package com.kerencev.vknewscompose.presentation.screens.comments

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.model.news_feed.CommentModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.common.compose.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.compose.RetryWithTitle
import com.kerencev.vknewscompose.presentation.common.compose.ScaffoldWithToolbar

@Composable
fun CommentsScreen(
    newsModel: NewsModel,
    onBackPressed: () -> Unit
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(
            application = LocalContext.current.applicationContext as Application,
            newsModel = newsModel
        )
    )
    val state = viewModel.screenState.collectAsState(ScreenState.Loading).value

    ScaffoldWithToolbar(
        title = newsModel.communityName,
        subTitle = stringResource(id = R.string.comments),
        onBackPressed = { onBackPressed() }
    ) { paddingValues ->
        when (state) {
            is ScreenState.Empty -> RetryWithTitle(
                title = stringResource(id = R.string.empty_comments),
                onRetryClick = viewModel::loadData
            )

            is ScreenState.Loading -> ProgressBarDefault()

            is ScreenState.Content -> CommentsList(
                comments = state.data,
                paddingValues = paddingValues
            )

            is ScreenState.Error -> RetryWithTitle(
                title = stringResource(id = R.string.something_went_wrong),
                onRetryClick = viewModel::loadData
            )
        }
    }
}

@Composable
fun CommentsList(
    comments: List<CommentModel>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues = paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 72.dp
        )
    ) {
        items(
            items = comments,
            key = { it.id }
        ) { comment ->
            CommentItem(comment = comment)
        }
    }
}

@Composable
fun CommentItem(
    comment: CommentModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = comment.authorImageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.authorName,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = comment.commentText,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = comment.commentDate,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}
