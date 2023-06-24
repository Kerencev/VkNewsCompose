package com.kerencev.vknewscompose.domain.entities

data class NewsModel(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val postTime: String,
    val communityImageUrl: String?,
    val contentText: String,
    val contentImageUrl: String?,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int,
    val isLiked: Boolean
)
