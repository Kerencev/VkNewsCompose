package com.kerencev.vknewscompose.domain.model

data class NewsModel(
    val id: Int,
    val name: String,
    val postTime: Long,
    val text: String,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int
)
