package com.kerencev.vknewscompose.presentation.screens.profile.views.cover

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.common.ContentState

@Composable
fun ProfileCover(
    boxScope: BoxScope,
    alpha: Float,
    profileType: ProfileType,
    profileState: ContentState<Profile>
) {
    val height = getHeightByType(profileType)

    if (profileState is ContentState.Content) {
        val coverUrl = profileState.data.coverUrl
        when (profileType) {
            ProfileType.USER -> UserProfileCover(
                boxScope = boxScope,
                alpha = alpha,
                coverUrl = coverUrl,
                height = height
            )

            ProfileType.GROUP -> GroupProfileCover(
                boxScope = boxScope,
                alpha = alpha,
                coverUrl = coverUrl,
                height = height
            )
        }
    }
}

private fun getHeightByType(profileType: ProfileType) = when (profileType) {
    ProfileType.USER -> 160.dp
    ProfileType.GROUP -> 148.dp
}
