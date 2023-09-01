package com.kerencev.vknewscompose.presentation.screens.suggested.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedInputAction

/**
 * Feature for getting suggested users, groups, pages
 */
interface GetSuggestedFeature : VkFeature<SuggestedInputAction.GetData>