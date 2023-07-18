package com.kerencev.vknewscompose.presentation.screens.main.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainInputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState

/**
 * Feature for checking user authorization
 */
interface CheckAuthStateFeature : VkFeature<MainInputAction.CheckAuthState, MainState>