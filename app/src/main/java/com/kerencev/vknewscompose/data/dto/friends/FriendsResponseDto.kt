package com.kerencev.vknewscompose.data.dto.friends

import com.google.gson.annotations.SerializedName

data class FriendsResponseDto(
    val response: FriendsDto?
)

data class FriendsDto(
    val count: Int?,
    val items: List<FriendDto>?
)

data class FriendDto(
    val id: Long,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    val online: Int?,
    @SerializedName("online_mobile") val onlineMobile: Int?,
    @SerializedName("photo_200") val avatarUrl: String?
)