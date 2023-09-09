package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.domain.entities.LastSeen
import com.kerencev.vknewscompose.domain.entities.OnlineType
import com.kerencev.vknewscompose.domain.entities.Platform
import com.kerencev.vknewscompose.domain.entities.UserProfileModel

fun ProfileDto.mapToModel(): UserProfileModel {
    return UserProfileModel(
        id = id ?: 0L,
        name = "${firstName.orEmpty()} ${lastName.orEmpty()}",
        lastName = lastName.orEmpty(),
        city = city?.title,
        universityName = universityName,
        avatarUrl = avatar200 ?: avatar100,
        coverUrl = avatar200 ?: avatar100,
        friendsCount = counters?.friends ?: 0,
        onlineType = getOnlineType(online, onlineMobile),
        lastSeen = getLastSeen(lastSeen?.time),
        platform = getPlatform(lastSeen?.platform)
    )
}

fun getOnlineType(online: Int?, onlineMobile: Int?): OnlineType {
    return if (onlineMobile == 1) OnlineType.ONLINE_MOBILE
    else if (online == 1) OnlineType.ONLINE
    else OnlineType.OFFLINE
}

fun getLastSeen(time: Long?): LastSeen {
    time ?: return LastSeen()
    val minutes: Long = (System.currentTimeMillis() - ((time) * 1_000)) / 60_000
    var hours: Long? = null
    var days: Long? = null
    if (minutes >= 60) {
        hours = minutes / 60
        if (hours >= 24) {
            days = hours / 24
        }
    }
    return LastSeen(
        days = days,
        hours = hours,
        minutes = minutes
    )
}

fun getPlatform(index: Int?): Platform {
    return when (index) {
        1 -> Platform.MOBILE
        2 -> Platform.IPHONE
        3 -> Platform.IPAD
        4 -> Platform.ANDROID
        5 -> Platform.WINDOWS_PHONE
        6 -> Platform.WINDOWS_10
        7 -> Platform.WEB
        else -> Platform.WEB
    }
}