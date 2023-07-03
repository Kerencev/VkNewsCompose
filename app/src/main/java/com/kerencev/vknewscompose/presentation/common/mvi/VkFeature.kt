package com.kerencev.vknewscompose.presentation.common.mvi

import kotlinx.coroutines.flow.Flow

/**
 * Created and launched when processing/sending/receiving data is required after user actions
 * Returns OutputAction or Effect on Success, Loading, Error
 */
interface VkFeature<A : VkAction, S : VkState> : (A, S) -> Flow<VkCommand>