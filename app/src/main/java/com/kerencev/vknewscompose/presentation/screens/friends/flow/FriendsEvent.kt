package com.kerencev.vknewscompose.presentation.screens.friends.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class FriendsEvent : VkEvent {

    class GetFriends(val isRefresh: Boolean) : FriendsEvent()

    class SearchFriends(val searchText: String) : FriendsEvent()
}