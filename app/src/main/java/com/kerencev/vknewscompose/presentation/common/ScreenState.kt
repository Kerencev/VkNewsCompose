package com.kerencev.vknewscompose.presentation.common

sealed class ScreenState<out T> {

    data class Content<T>(val data: T) : ScreenState<T>()

    object Loading : ScreenState<Nothing>()

    object Empty : ScreenState<Nothing>()

    data class Error<T>(val throwable: Throwable) : ScreenState<T>()
}
