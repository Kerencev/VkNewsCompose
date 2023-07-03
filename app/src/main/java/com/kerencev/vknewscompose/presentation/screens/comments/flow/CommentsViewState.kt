package com.kerencev.vknewscompose.presentation.screens.comments.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

/**
 * @param commentsList - list of comments items
 * @param isLoading - data loading flag
 * @param isError - flag for displaying an error that occurred when loading data
 */
@Stable
data class CommentsViewState(
    val commentsList: List<CommentModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : VkState {

    fun comments(comments: List<CommentModel>) =
        CommentsViewState(commentsList = comments)

    fun loading() = copy(
        isLoading = true,
        isError = false
    )

    fun error() = copy(
        isLoading = false,
        isError = true
    )
}
