package com.kerencev.vknewscompose.domain.entities

data class FriendsModel(
    val friends: List<UserProfileModel>,
    val isFriendsOver: Boolean
)
