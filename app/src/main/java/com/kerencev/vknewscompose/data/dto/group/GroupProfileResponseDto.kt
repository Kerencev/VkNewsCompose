package com.kerencev.vknewscompose.data.dto.group

import com.google.gson.annotations.SerializedName
import com.kerencev.vknewscompose.data.dto.news_feed.SizeX

data class GroupProfileResponseDto(
    val response: List<GroupProfileDto>?
)

data class GroupProfileDto(
    val id: Long?,
    val cover: Cover?,
    @SerializedName("photo_200") val avatarUrl: String?,
    val name: String?,
    @SerializedName("screen_name") val screenName: String?,
    @SerializedName("is_closed") val isClosed: Int?,
    val type: String?
)

data class Cover(
    val images: List<SizeX>?
)