package com.kerencev.vknewscompose.presentation.screens.comments

import com.kerencev.vknewscompose.domain.model.news_feed.CommentModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val newsModel: NewsModel,
        val comments: List<CommentModel>
    ) : CommentsScreenState()

}
