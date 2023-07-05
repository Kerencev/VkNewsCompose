package com.kerencev.vknewscompose.presentation.common

sealed class ContentState<out T> {

    data class Content<T>(val data: T) : ContentState<T>()

    object Loading : ContentState<Nothing>()

    data class Error<T>(val message: String) : ContentState<T>()
}
