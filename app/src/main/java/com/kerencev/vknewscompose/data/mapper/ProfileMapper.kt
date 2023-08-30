package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.domain.entities.UserProfileModel

fun ProfileDto.mapToModel(): UserProfileModel {
    return UserProfileModel(
        id = id ?: 0L,
        name = "${firstName.orEmpty()} ${lastName.orEmpty()}",
        lastName = lastName.orEmpty(),
        city = city?.title,
        universityName = universityName,
        avatarUrl = avatarUrl,
        coverUrl = avatarUrl,
        friendsCount = counters?.friends ?: 0
    )
}