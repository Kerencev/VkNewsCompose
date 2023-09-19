package com.kerencev.vknewscompose.presentation.common.views.status

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.LastSeen
import com.kerencev.vknewscompose.domain.entities.OnlineType
import com.kerencev.vknewscompose.domain.entities.Platform

@Composable
fun UserStatus(
    boxScope: BoxScope,
    onlineType: OnlineType,
    lastSeen: LastSeen,
    platform: Platform,
    alignment: Alignment,
    alpha: Float = 1f,
    clipTextShape: Shape = RoundedCornerShape(16.dp),
    clipIconShape: Shape = CircleShape,
    iconSize: Dp = 16.dp,
    paddingValues: PaddingValues = PaddingValues(),
) {
    boxScope.run {
        when (onlineType) {
            OnlineType.OFFLINE -> UserStatusOffline(
                boxScope = this,
                lastSeen = lastSeen,
                alignment = alignment,
                alpha = alpha,
                clipTextShape = clipTextShape,
                paddingValues = paddingValues
            )

            OnlineType.ONLINE, OnlineType.ONLINE_MOBILE -> UserStatusOnline(
                boxScope = this,
                platform = platform,
                alignment = alignment,
                alpha = alpha,
                clipIconShape = clipIconShape,
                iconSize = iconSize,
                paddingValues = paddingValues
            )
        }
    }
}
