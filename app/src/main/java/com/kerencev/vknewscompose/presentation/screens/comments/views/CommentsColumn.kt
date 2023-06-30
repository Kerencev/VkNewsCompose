package com.kerencev.vknewscompose.presentation.screens.comments.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.CommentModel

@Composable
fun CommentsColumn(
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
        items(items = comments, key = { it.id }) { comment ->
            CommentItem(comment = comment)
        }
    }
}