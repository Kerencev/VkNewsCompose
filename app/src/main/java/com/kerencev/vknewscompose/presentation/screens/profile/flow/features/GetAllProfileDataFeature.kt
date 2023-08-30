package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction

/**
 * Feature to get all data for the profile screen
 * Used when the user uses swipe refresh
 */
interface GetAllProfileDataFeature : VkFeature<ProfileInputAction.RefreshProfileData>