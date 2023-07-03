package com.kerencev.vknewscompose.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.retry

private const val FLOW_RETRY_COUNT = 2L
private const val FLOW_RETRY_DELAY = 3_000L

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}

fun <T> Flow<T>.retryDefault(): Flow<T> {
    return retry(FLOW_RETRY_COUNT) {
        delay(FLOW_RETRY_DELAY)
        true
    }
}