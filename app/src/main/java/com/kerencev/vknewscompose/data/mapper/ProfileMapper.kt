package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.domain.entities.ProfileModel

fun ProfileDto.mapToModel(): ProfileModel {
    return ProfileModel(
        id = id ?: 0L,
        name = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        city = city?.title,
        universityName = universityName,
        avatarUrl = avatarUrl,
        friendsCount = counters?.friends ?: 0
    )
}