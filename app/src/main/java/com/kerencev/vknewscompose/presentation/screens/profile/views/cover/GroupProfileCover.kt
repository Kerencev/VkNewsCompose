package com.kerencev.vknewscompose.presentation.screens.profile.views.cover

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.kerencev.vknewscompose.presentation.common.views.loading.AsyncShimmerImage

@Composable
fun GroupProfileCover(
    boxScope: BoxScope,
    alpha: Float,
    coverUrl: String?,
    height: Dp,
) {
    boxScope.run {
        AsyncShimmerImage(
            modifier = Modifier
                .alpha(alpha)
                .fillMaxWidth()
                .height(height)
                .align(Alignment.TopCenter),
            imageUrl = coverUrl,
            shimmerHeight = height
        )
    }
}