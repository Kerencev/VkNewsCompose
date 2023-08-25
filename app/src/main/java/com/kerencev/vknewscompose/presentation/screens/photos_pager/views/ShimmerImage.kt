package com.kerencev.vknewscompose.presentation.screens.photos_pager.views

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp)
                .shimmer(
                    customShimmer = rememberShimmer(
                        shimmerBounds = ShimmerBounds.View,
                        theme = verticalShimmerTheme
                    )
                ),
            painter = painterResource(id = R.drawable.ic_photo_24),
            contentDescription = null
        )
    }
}

val verticalShimmerTheme = ShimmerTheme(
    animationSpec = infiniteRepeatable(
        animation = tween(
            1_500,
            easing = FastOutLinearInEasing,
            delayMillis = 0,
        ),
        repeatMode = RepeatMode.Reverse,
    ),
    blendMode = BlendMode.SrcOver,
    rotation = 0.0f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 0.25f),
        Color.Unspecified.copy(alpha = 1.00f),
        Color.Unspecified.copy(alpha = 0.25f),
    ),
    shaderColorStops = listOf(
        0.0f,
        0.5f,
        1.0f,
    ),
    shimmerWidth = 200.dp,
)