package com.kerencev.vknewscompose.domain.entities

data class FriendsModel(
    val friends: List<FriendModel>,
    val isFriendsOver: Boolean
)

data class FriendModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val isOnline: Boolean,
    val isOnlineMobile: Boolean,
    val avatarUrl: String?
)
