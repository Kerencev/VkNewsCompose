package com.kerencev.vknewscompose.domain.entities

interface Profile {
    val id: Long
    val name: String
    val avatarUrl: String?
    val coverUrl: String?
}

data class UserProfileModel(
    override val id: Long,
    override val name: String,
    override val avatarUrl: String?,
    override val coverUrl: String?,
    val lastName: String,
    val city: String?,
    val universityName: String?,
    val friendsCount: Int,
    val onlineType: OnlineType,
    val lastSeen: LastSeen,
    val platform: Platform
) : Profile

data class GroupProfileModel(
    override val id: Long,
    override val name: String,
    override val avatarUrl: String?,
    override val coverUrl: String?,
) : Profile

enum class OnlineType {
    OFFLINE, ONLINE, ONLINE_MOBILE
}

data class LastSeen(
    val days: Long? = null,
    val hours: Long? = null,
    val minutes: Long = 0
)

enum class Platform {
    MOBILE, IPHONE, IPAD, ANDROID, WINDOWS_PHONE, WINDOWS_10, WEB
}
