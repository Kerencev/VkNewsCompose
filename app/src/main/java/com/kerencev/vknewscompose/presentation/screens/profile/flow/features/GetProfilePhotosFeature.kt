package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.common.mvi.VkState
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction

/**
 * Get all profile photos
 */
interface GetProfilePhotosFeature : VkFeature<ProfileInputAction.GetProfilePhotos, VkState>