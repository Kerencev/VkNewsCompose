package com.kerencev.vknewscompose.domain.model.news_feed

data class CommentModel(
    val id: Long,
    val authorName: String,
    val authorImageUrl: String?,
    val commentText: String,
    val commentDate: String
)
