package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.suggested.SuggestedDto
import com.kerencev.vknewscompose.data.dto.suggested.SuggestedType
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.UserProfileModel

fun SuggestedDto.mapToModel(): Profile {
    return when (type) {
        SuggestedType.profile -> {
            UserProfileModel(
                id = id ?: 0,
                name = "$firstName $lastName",
                avatarUrl = photo,
                coverUrl = null,
                lastName = lastName.orEmpty(),
                city = null,
                universityName = null,
                friendsCount = 0,
                onlineType = getOnlineType(online, onlineMobile),
                lastSeen = getLastSeen(lastSeen?.time),
                platform = getPlatform(platform)
            )
        }

        SuggestedType.page, SuggestedType.group, null -> {
            GroupProfileModel(
                id = id ?: 0,
                name = name.orEmpty(),
                avatarUrl = photo,
                coverUrl = cover?.images?.last()?.url,
                memberCount = memberCount ?: 0
            )
        }
    }
}