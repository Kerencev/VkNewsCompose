package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.ContentState

@Composable
fun ProfileHeader(
    profileState: ContentState<ProfileModel>,
    avatarAlpha: Float,
    avatarSize: Dp,
    onRetryClick: () -> Unit
) {
    when (profileState) {
        is ContentState.Content -> ProfileHeaderContent(
            model = profileState.data,
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize
        )

        is ContentState.Loading -> ProfileHeaderLoading(
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize
        )

        is ContentState.Error -> ProfileHeaderError(
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize,
            onRetryClick = onRetryClick
        )
    }
}