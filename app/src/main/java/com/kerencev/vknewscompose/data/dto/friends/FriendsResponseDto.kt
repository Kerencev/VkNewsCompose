package com.kerencev.vknewscompose.data.dto.friends

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto

data class FriendsResponseDto(
    val response: FriendsDto?,
)

data class FriendsDto(
    val count: Int?,
    val items: List<ProfileDto>?,
)
