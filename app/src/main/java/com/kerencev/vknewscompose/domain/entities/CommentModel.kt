package com.kerencev.vknewscompose.domain.entities

data class CommentModel(
    val id: Long,
    val authorName: String,
    val authorImageUrl: String?,
    val commentText: String,
    val commentDate: String
)
