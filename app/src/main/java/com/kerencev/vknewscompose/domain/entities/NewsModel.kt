package com.kerencev.vknewscompose.domain.entities

data class NewsModel(
    val id: Long,
    val type: ProfileType,
    val ownerId: Long,
    val communityName: String,
    val postTime: String,
    val communityImageUrl: String?,
    val contentText: String,
    val imageContent: List<ImageContentModel>,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int,
    val isLiked: Boolean
)

data class ImageContentModel(
    val id: Long,
    val url: String,
    val height: Int,
    val width: Int,
)
