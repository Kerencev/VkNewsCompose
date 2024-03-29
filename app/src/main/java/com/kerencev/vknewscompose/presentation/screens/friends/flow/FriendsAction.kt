package com.kerencev.vknewscompose.presentation.screens.friends.flow

import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class FriendsInputAction : VkAction {

    /**
     * Get list of users's friends
     * @param userId - Id of the user for whom the friends list will be uploaded
     * @param searchText - The text by which the list of friends will be received
     * @param isRefresh - The list of friends is updated or added
     */
    class GetFriends(val userId: Long, val searchText: String, val isRefresh: Boolean) :
        FriendsInputAction()
}

sealed class FriendsOutputAction : VkAction {

    /**
     * Set friends list on success
     * @param isFriendsOver - Is the list of friends over
     */
    class SetFriends(
        val searchText: String,
        val friends: List<UserProfileModel>,
        val isFriendsOver: Boolean
    ) : FriendsOutputAction()

    /**
     * Set loading state
     * @param isRefresh - The list of friends is updated or added
     */
    class Loading(val isRefresh: Boolean) : FriendsOutputAction()

    /**
     * Set error state
     */
    class Error(val message: String) : FriendsOutputAction()

}