package com.kerencev.vknewscompose.presentation.screens.friends.flow.features

import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsInputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsOutputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetFriendsFeatureImpl @Inject constructor(
    private val friendsRepository: FriendsRepository
) : GetFriendsFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(
        action: FriendsInputAction.GetFriends,
        state: FriendsViewState
    ): Flow<VkCommand> {
        return friendsRepository.getFriends(
            userId = action.userId,
            searchText = action.searchText,
            isRefresh = action.isRefresh
        )
            .flatMapConcat { friends ->
                flowOf(
                    FriendsOutputAction.SetFriends(
                        searchText = action.searchText,
                        friends = friends.friends,
                        isFriendsOver = friends.isFriendsOver
                    ) as FriendsOutputAction
                )
            }
            .onStart { emit(FriendsOutputAction.Loading(action.isRefresh)) }
            .retryDefault()
            .catch { emit(FriendsOutputAction.Error(it.message.orEmpty())) }
    }
}