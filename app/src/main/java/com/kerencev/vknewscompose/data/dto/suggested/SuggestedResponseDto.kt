package com.kerencev.vknewscompose.data.dto.suggested

import com.google.gson.annotations.SerializedName
import com.kerencev.vknewscompose.data.dto.group.Cover
import com.kerencev.vknewscompose.data.dto.profile.LastSeenDto

data class SuggestedResponseDto(
    val response: SuggestedListDto?
)

data class SuggestedListDto(
    val count: Int?,
    val items: List<SuggestedDto>?
)

data class SuggestedDto(
    val id: Long?,
    val name: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    val online: Int?,
    @SerializedName("online_mobile") val onlineMobile: Int?,
    @SerializedName("photo_200") val photo: String?,
    val type: SuggestedType?,
    @SerializedName("last_seen") val lastSeen: LastSeenDto?,
    val cover: Cover?,
    @SerializedName("members_count") val memberCount: Int?,
    val platform: Int?,
)

enum class SuggestedType {
    page, group, profile
}