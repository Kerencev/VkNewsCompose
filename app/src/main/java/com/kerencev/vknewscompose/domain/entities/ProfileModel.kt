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
) : Profile

data class GroupProfileModel(
    override val id: Long,
    override val name: String,
    override val avatarUrl: String?,
    override val coverUrl: String?,
) : Profile
