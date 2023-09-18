package com.kerencev.vknewscompose.data.dto.comments

import com.google.gson.annotations.SerializedName
import com.kerencev.vknewscompose.data.dto.news_feed.Group
import com.kerencev.vknewscompose.data.dto.profile.ProfileDto

data class CommentsResponseDto(
    val response: CommentsDto?
)

data class CommentsDto(
    val count: Int?,
    val items: List<CommentDto>?,
    val profiles: List<ProfileDto>?,
    val groups: List<Group>?,
)

data class CommentDto(
    val id: Long,
    @SerializedName("from_id") val fromId: Long?,
    @SerializedName("post_id") val postId: Long?,
    @SerializedName("owner_id") val ownerId: Long?,
    val text: String?,
    val date: Long?
)