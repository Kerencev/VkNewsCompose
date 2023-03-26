package com.kerencev.vknewscompose.domain.model

data class CommentModel(
    val id: Int,
    val authorName: String,
    val commentText: String,
    val commentDate: Long
)
