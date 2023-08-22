package com.kerencev.vknewscompose.presentation.screens.friends

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsEvent
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsInputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsOutputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsViewState
import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getFriendsFeature: GetFriendsFeature
) : BaseViewModel<FriendsEvent, FriendsViewState, VkShot>() {

    override fun initState() = FriendsViewState()

    override fun produceCommand(event: FriendsEvent): VkCommand {
        return when (event) {
            is FriendsEvent.GetFriends -> {
                FriendsInputAction.GetFriends(
                    searchText = event.searchText,
                    isRefresh = event.isRefresh
                )
            }
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is FriendsInputAction.GetFriends -> {
                getFriendsFeature(action, state())
            }

            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is FriendsOutputAction.SetFriends -> setState {
                setFriends(
                    friends = action.friends,
                    isFriendsOver = action.isFriendsOver
                )
            }

            is FriendsOutputAction.Loading -> setState { loading(action.isRefresh) }
            is FriendsOutputAction.Error -> setState { error(action.message) }
        }
    }
}