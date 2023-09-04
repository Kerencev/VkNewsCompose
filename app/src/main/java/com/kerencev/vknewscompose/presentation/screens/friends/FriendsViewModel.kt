package com.kerencev.vknewscompose.presentation.screens.friends

import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.common.mvi.state.SearchViewState
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsEvent
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsInputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsOutputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val userId: Long,
    private val getFriendsFeature: GetFriendsFeature
) : BaseViewModel<FriendsEvent, SearchViewState<UserProfileModel>, VkShot>() {

    override fun initState() = SearchViewState<UserProfileModel>()

    override fun produceCommand(event: FriendsEvent): VkCommand {
        return when (event) {
            is FriendsEvent.GetFriends -> {
                FriendsInputAction.GetFriends(
                    userId = userId,
                    searchText = state().query,
                    isRefresh = event.isRefresh
                )
            }

            is FriendsEvent.SearchFriends -> handleSearchEvent(event)
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is FriendsInputAction.GetFriends -> getFriendsFeature(action)
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is FriendsOutputAction.SetFriends -> setState {
                setData(
                    query = action.searchText,
                    items = action.friends,
                    isItemsOver = action.isFriendsOver
                )
            }

            is FriendsOutputAction.Loading -> setState {
                setLoading(
                    isRefresh = action.isRefresh,
                    query = query
                )
            }

            is FriendsOutputAction.Error -> setState {
                setError(
                    message = action.message,
                    query = query
                )
            }
        }
    }

    private fun handleSearchEvent(event: FriendsEvent.SearchFriends): VkAction {
        val searchText = event.searchText
        return if (isSearchParametersSame(searchText)) {
            FriendsOutputAction.SetFriends(
                searchText = searchText,
                friends = state().items,
                isFriendsOver = state().isItemsOver
            )
        } else {
            FriendsInputAction.GetFriends(
                userId = userId,
                searchText = searchText,
                isRefresh = true
            )
        }
    }

    private fun isSearchParametersSame(searchText: String): Boolean {
        return (searchText.isEmpty() && state().items.isEmpty() && !state().isItemsOver) ||
                searchText == state().query
    }
}