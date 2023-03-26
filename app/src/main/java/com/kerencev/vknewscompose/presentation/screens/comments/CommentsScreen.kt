package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.model.CommentModel
import com.kerencev.vknewscompose.domain.model.NewsModel
import com.kerencev.vknewscompose.extensions.toDateTime

@Composable
fun CommentsScreen(
    newsModel: NewsModel,
    comments: List<CommentModel>,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${stringResource(id = R.string.comments)} ${newsModel.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
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
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
                text = comment.commentDate.toDateTime(),
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}
