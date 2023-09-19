package com.kerencev.vknewscompose.presentation.screens.profile.views.header

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.common.ContentState

@Composable
fun ProfileHeader(
    profileType: ProfileType,
    profileState: ContentState<Profile>,
    avatarAlpha: Float,
    avatarSize: Dp,
    onRetryClick: () -> Unit
) {
    val contentPadding = PaddingValues(top = avatarSize / 2)
    val contentAlignment = getContentAlignment(profileType = profileType)
    val avatarPadding = getAvatarPadding(profileType = profileType)
    val avatarAlign = getAvatarAlign(profileType = profileType)

    when (profileState) {
        is ContentState.Content -> ProfileHeaderContent(
            profile = profileState.data,
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize,
            contentPadding = contentPadding,
            contentAlignment = contentAlignment,
            avatarPadding = avatarPadding,
            avatarAlign = avatarAlign,
        )

        is ContentState.Loading -> ProfileHeaderLoading(
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize,
            contentPadding = contentPadding,
            avatarPadding = avatarPadding,
            avatarAlign = avatarAlign,
        )

        is ContentState.Error -> ProfileHeaderError(
            avatarAlpha = avatarAlpha,
            avatarSize = avatarSize,
            contentPadding = contentPadding,
            contentAlignment = contentAlignment,
            avatarPadding = avatarPadding,
            avatarAlign = avatarAlign,
            onRetryClick = onRetryClick
        )
    }
}

private fun getContentAlignment(profileType: ProfileType) = when (profileType) {
    ProfileType.USER -> Alignment.CenterHorizontally
    ProfileType.GROUP -> Alignment.Start
}

private fun getAvatarPadding(profileType: ProfileType) = when (profileType) {
    ProfileType.USER -> PaddingValues()
    ProfileType.GROUP -> PaddingValues(start = 16.dp)
}

private fun getAvatarAlign(profileType: ProfileType) = when (profileType) {
    ProfileType.USER -> Alignment.TopCenter
    ProfileType.GROUP -> Alignment.TopStart
}
