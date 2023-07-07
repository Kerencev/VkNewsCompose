package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState

/**
 * Get wall posts
 * Feature has a pagination and a message about the end of posts
 */
interface GetWallFeature : VkFeature<ProfileInputAction.GetWall, ProfileViewState>