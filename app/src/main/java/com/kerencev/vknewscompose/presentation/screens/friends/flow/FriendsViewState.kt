package com.kerencev.vknewscompose.presentation.screens.friends.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.FriendModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class FriendsViewState(
    val friendsList: List<FriendModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFriendsOver: Boolean = false,
    val isSwipeRefreshing: Boolean = false,
    val searchText: String = ""
) : VkState {

    fun setFriends(searchText: String, friends: List<FriendModel>, isFriendsOver: Boolean) = copy(
        friendsList = friends,
        isLoading = false,
        errorMessage = null,
        isFriendsOver = isFriendsOver,
        isSwipeRefreshing = false,
        searchText = searchText
    )

    fun loading(isRefresh: Boolean) = copy(
        isLoading = !isRefresh,
        errorMessage = null,
        isSwipeRefreshing = isRefresh
    )

    fun error(errorMessage: String) = copy(
        isLoading = false,
        errorMessage = errorMessage,
        isSwipeRefreshing = false
    )
}
