package com.kerencev.vknewscompose.presentation.screens.home.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState

/**
 * Feature for getting a list of news
 */
interface GetNewsFeature : VkFeature<HomeInputAction.GetNews, HomeViewState>