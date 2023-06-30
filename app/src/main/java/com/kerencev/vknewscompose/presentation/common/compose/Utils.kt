package com.kerencev.vknewscompose.presentation.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * функции обертки для оптимизации Compose при работе с лямбдами
 */
@Composable
fun rememberUnit(action: () -> Unit): () -> Unit {
    return remember() { { action() } }
}

@Composable
fun rememberUnit(key: Any, action: () -> Unit): () -> Unit {
    return remember(key) { { action() } }
}

@Composable
fun rememberUnit(key1: Any, key2: Any, action: () -> Unit): () -> Unit {
    return remember(key1, key2) { { action() } }
}

@Composable
fun rememberUnit(key1: Any, key2: Any, key3: Any, action: () -> Unit): () -> Unit {
    return remember(key1, key2, key3) { { action() } }
}

@Composable
fun rememberUnit(vararg keys: Any?, action: () -> Unit): () -> Unit {
    return remember(keys) { { action() } }
}


@Composable
inline fun <T> rememberUnitParams(crossinline action: (T) -> Unit): (T) -> Unit {
    return remember() {
        {
            action.invoke(it)
        }
    }
}

@Composable
inline fun <T> rememberUnitParams(key: Any, crossinline action: (T) -> Unit): (T) -> Unit {
    return remember(key) {
        {
            action.invoke(it)
        }
    }
}

@Composable
inline fun <T> rememberUnitParams(
    key1: Any,
    key2: Any,
    crossinline action: (T) -> Unit
): (T) -> Unit {
    return remember(key1, key2) {
        {
            action.invoke(it)
        }
    }
}

@Composable
inline fun <T> rememberUnitParams(
    key1: Any,
    key2: Any,
    key3: Any,
    crossinline action: (T) -> Unit
): (T) -> Unit {
    return remember(key1, key2, key3) {
        {
            action.invoke(it)
        }
    }
}

@Composable
inline fun <T> rememberUnitParams(
    vararg keys: Any?,
    crossinline action: (T) -> Unit
): (T) -> Unit {
    return remember(keys) {
        {
            action.invoke(it)
        }
    }
}