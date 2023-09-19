package com.kerencev.vknewscompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Black900,
    primaryVariant = Black900,
    secondary = Black700,
    onPrimary = Color.White,
    onSecondary = Black500
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Black200,
    onPrimary = Black900,
    onSecondary = Black500
)

@Composable
fun VkNewsComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}