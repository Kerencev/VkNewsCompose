package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

/**
 * Get all profile photos
 */
interface GetProfilePhotosFeature : VkFeature<VkAction, VkState>