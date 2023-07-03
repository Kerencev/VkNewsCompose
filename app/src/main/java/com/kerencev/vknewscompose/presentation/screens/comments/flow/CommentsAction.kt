package com.kerencev.vknewscompose.presentation.screens.comments.flow

import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class CommentsInputAction : VkAction {

    /**
     * Get list of comments
     */
    class GetComments(val newsModel: NewsModel) : CommentsInputAction()

}

sealed class CommentsOutputAction : VkAction {

    /**
     * Set comments list on success
     */
    class SetComments(val result: List<CommentModel>) : CommentsOutputAction()

    /**
     * Set loading state
     */
    object Loading : CommentsOutputAction()

    /**
     * Set error state
     */
    class Error(val message: String) : CommentsOutputAction()

}