package com.kerencev.vknewscompose.presentation.common

import com.kerencev.vknewscompose.presentation.common.mvi.VkState

sealed class ContentState<out T> : VkState {

    data class Content<T>(val data: T) : ContentState<T>()

    object Loading : ContentState<Nothing>()

    data class Error(val message: String) : ContentState<Nothing>()
}
