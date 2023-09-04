package com.kerencev.vknewscompose.presentation.screens.profile.views.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.ui.theme.LightGray
import com.kerencev.vknewscompose.ui.theme.Shapes
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileHeaderLoading(
    profileType: ProfileType,
    avatarAlpha: Float,
    avatarSize: Dp,
    contentPadding: PaddingValues,
    avatarPadding: PaddingValues,
    avatarAlign: Alignment,
) {
    val height = if (profileType == ProfileType.USER) 120.dp else 80.dp
    Box {
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .clip(Shapes.large)
                .height(height)
                .fillMaxWidth()
                .shimmer()
                .background(LightGray)
        )

        Image(
            modifier = Modifier
                .padding(avatarPadding)
                .alpha(avatarAlpha)
                .size(avatarSize)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                .align(avatarAlign)
                .shimmer()
                .background(LightGray),
            painter = painterResource(id = R.drawable.ic_people),
            contentDescription = null,
        )
    }
}