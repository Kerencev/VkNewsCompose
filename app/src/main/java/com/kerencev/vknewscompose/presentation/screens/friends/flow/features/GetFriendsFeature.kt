package com.kerencev.vknewscompose.presentation.screens.friends.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsInputAction
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsViewState

interface GetFriendsFeature : VkFeature<FriendsInputAction.GetFriends, FriendsViewState>