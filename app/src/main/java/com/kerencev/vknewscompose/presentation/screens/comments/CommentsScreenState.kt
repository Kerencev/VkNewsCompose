package com.kerencev.vknewscompose.presentation.screens.comments

import com.kerencev.vknewscompose.domain.model.CommentModel
import com.kerencev.vknewscompose.domain.model.NewsModel

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val newsModel: NewsModel,
        val comments: List<CommentModel>
    ) : CommentsScreenState()

}
