package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.group.GroupProfileDto
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel

fun GroupProfileDto.mapToModel(): GroupProfileModel {
    return GroupProfileModel(
        id = id ?: 0L,
        coverUrl = cover?.images?.last()?.url,
        avatarUrl = avatarUrl,
        name = name.orEmpty(),
        memberCount = memberCount ?: 0,
        description = description.orEmpty()
    )
}