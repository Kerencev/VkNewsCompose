package com.kerencev.vknewscompose.domain.entities

data class ProfileModel(
    val id: Long,
    val name: String,
    val lastName: String,
    val city: String?,
    val universityName: String?,
    val avatarUrl: String?,
    val friendsCount: Int,
)
