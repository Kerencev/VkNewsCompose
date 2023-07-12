package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState

/**
 * Feature that helps to calculate the parameters necessary for the profile UI
 */
interface CalculateProfileParamsFeature :
    VkFeature<ProfileInputAction.CalculateUiParams, ProfileViewState>