package com.kerencev.vknewscompose.data.dto.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    val response: CommentsDto?
)

data class CommentsDto(
    val count: Int?,
    val items: List<CommentDto>?,
    val profiles: List<ProfileDto>?
)

data class CommentDto(
    val id: Long,
    @SerializedName("from_id") val fromId: Long?,
    @SerializedName("post_id") val postId: Int?,
    @SerializedName("owner_id") val ownerId: Int?,
    val text: String?,
    val date: Long?
)