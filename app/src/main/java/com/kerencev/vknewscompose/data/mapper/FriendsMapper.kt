package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.friends.FriendDto
import com.kerencev.vknewscompose.domain.entities.FriendModel

fun FriendDto.mapToModel(): FriendModel {
    return FriendModel(
        id = id,
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        isOnline = online == 1,
        isOnlineMobile = onlineMobile == 1,
        avatarUrl = avatarUrl
    )
}