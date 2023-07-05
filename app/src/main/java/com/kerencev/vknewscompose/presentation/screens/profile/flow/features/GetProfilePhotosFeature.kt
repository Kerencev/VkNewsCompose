package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState

/**
 * Get all profile photos
 */
interface GetPhotosFeature : VkFeature<ProfileInputAction.GetPhotos, ProfileViewState>