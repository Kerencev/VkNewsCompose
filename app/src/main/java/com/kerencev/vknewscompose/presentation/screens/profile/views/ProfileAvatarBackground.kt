package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileAvatarBackground(
    boxScope: BoxScope,
    alpha: Float,
    backgroundModel: Any?
) {
    boxScope.run {
        AsyncImage(
            modifier = Modifier
                .alpha(alpha)
                .fillMaxWidth()
                .height(160.dp)
                .blur(
                    radius = 32.dp,
                    edgeTreatment = BlurredEdgeTreatment(null)
                )
                .scale(5.0f)
                .align(Alignment.TopCenter),
            model = backgroundModel,
            contentDescription = null
        )
    }
}