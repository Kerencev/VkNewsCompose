package com.kerencev.vknewscompose.presentation.screens.comments.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

@Composable
fun CommentsColumn(
    comments: List<CommentModel>,
    paddingValues: PaddingValues,
    onCommentClick: (params: ProfileParams) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .background(MaterialTheme.colors.surface),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 72.dp
        )
    ) {
        items(items = comments, key = { it.id }) { comment ->
            CommentItem(
                modifier = Modifier.clickable {
                    onCommentClick(ProfileParams(id = comment.fromId, type = comment.type))
                },
                comment = comment
            )
        }
    }
}