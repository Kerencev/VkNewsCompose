package com.kerencev.vknewscompose.common

sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Error(val throwable: Throwable) : DataResult<Nothing>()

}
