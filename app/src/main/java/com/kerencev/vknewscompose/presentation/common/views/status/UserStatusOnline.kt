package com.kerencev.vknewscompose.presentation.common.views.status

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.Platform
import com.kerencev.vknewscompose.ui.theme.Green

@Composable
fun UserStatusOnline(
    boxScope: BoxScope,
    platform: Platform,
    alignment: Alignment,
    alpha: Float = 1f,
    clipIconShape: Shape = CircleShape,
    iconSize: Dp = 16.dp,
    paddingValues: PaddingValues = PaddingValues(),
) {
    boxScope.run {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .alpha(alpha)
                .clip(clipIconShape)
                .background(MaterialTheme.colors.surface)
                .align(alignment),
        ) {
            Image(
                modifier = Modifier
                    .padding(2.dp)
                    .size(iconSize),
                painter = painterResource(id = getPlatformIconId(platform)),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Green)
            )
        }
    }

}

@DrawableRes
private fun getPlatformIconId(platform: Platform): Int {
    return when (platform) {
        Platform.MOBILE -> R.drawable.ic_smartphone
        Platform.IPHONE -> R.drawable.ic_apple
        Platform.IPAD -> R.drawable.ic_apple
        Platform.ANDROID -> R.drawable.ic_android
        Platform.WINDOWS_PHONE -> R.drawable.ic_windows
        Platform.WINDOWS_10 -> R.drawable.ic_windows
        Platform.WEB -> R.drawable.ic_web
    }
}