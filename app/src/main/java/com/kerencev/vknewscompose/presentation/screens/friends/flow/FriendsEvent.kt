package com.kerencev.vknewscompose.presentation.screens.friends.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class FriendsEvent : VkEvent {

    /**
     * Get list of users's friends
     * @param searchText - The text by which the list of friends will be received
     * @param isRefresh - The list of friends is updated or added
     */
    class GetFriends(val isRefresh: Boolean) :
        FriendsEvent()

    class SearchFriends(val searchText: String) :
        FriendsEvent()
}