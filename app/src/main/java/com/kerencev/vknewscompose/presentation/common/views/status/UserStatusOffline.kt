package com.kerencev.vknewscompose.presentation.common.views.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.LastSeen

@Composable
fun UserStatusOffline(
    boxScope: BoxScope,
    lastSeen: LastSeen,
    alignment: Alignment = Alignment.TopStart,
    alpha: Float = 1f,
    clipTextShape: Shape = RoundedCornerShape(16.dp),
    paddingValues: PaddingValues = PaddingValues(),
) {
    boxScope.run {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .alpha(alpha)
                .clip(clipTextShape)
                .background(MaterialTheme.colors.secondary)
                .border(2.dp, MaterialTheme.colors.surface, clipTextShape)
                .align(alignment)
        ) {
            LastSeenProfile(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                lastSeen = lastSeen
            )
        }
    }
}