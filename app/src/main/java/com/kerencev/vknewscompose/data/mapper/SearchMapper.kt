package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.search.SearchType
import com.kerencev.vknewscompose.data.dto.suggested.SuggestedType
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.UserProfileModel

fun SearchType.mapToModel() = when (type) {
    SuggestedType.profile -> {
        UserProfileModel(
            id = profile?.id ?: 0,
            name = "${profile?.firstName} ${profile?.lastName}",
            avatarUrl = profile?.photo,
            coverUrl = null,
            lastName = profile?.lastName.orEmpty(),
            city = null,
            universityName = null,
            friendsCount = 0,
            onlineType = getOnlineType(profile?.online, profile?.onlineMobile),
            lastSeen = getLastSeen(profile?.lastSeen?.time),
            platform = getPlatform(profile?.platform)
        )
    }

    SuggestedType.group, null -> GroupProfileModel(
        id = -(group?.id ?: 0),
        name = group?.name.orEmpty(),
        avatarUrl = group?.photo,
        coverUrl = group?.cover?.images?.last()?.url,
        memberCount = group?.memberCount ?: 0,
        description = group?.description.orEmpty()
    )
}