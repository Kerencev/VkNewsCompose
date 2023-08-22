package com.kerencev.vknewscompose.presentation.common.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Wrapper functions for optimizing Compose when working with lambdas
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

@Composable
fun SetupSystemBar(
    isNavigationBarContrastEnforced: Boolean = true,
    darkIcons: Boolean = !isSystemInDarkTheme(),
    color: Color = Color.Transparent
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = darkIcons,
            isNavigationBarContrastEnforced = isNavigationBarContrastEnforced,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}