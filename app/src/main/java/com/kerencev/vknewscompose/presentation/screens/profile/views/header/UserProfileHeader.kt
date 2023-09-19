package com.kerencev.vknewscompose.presentation.screens.profile.views.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.views.icon.IconWithText

@Composable
fun UserProfileHeader(
    modifier: Modifier = Modifier,
    profile: UserProfileModel
) {
    Row(modifier = modifier) {
        if (!profile.city.isNullOrBlank()) {
            IconWithText(
                modifier = Modifier.weight(1f),
                text = profile.city,
                iconRes = R.drawable.ic_location,
                horizontalArrangement = Arrangement.Center
            )
        }
        if (!profile.universityName.isNullOrBlank()) {
            IconWithText(
                modifier = Modifier.weight(1f),
                text = profile.universityName,
                iconRes = R.drawable.ic_hat_education,
                horizontalArrangement = Arrangement.Center
            )
        }
    }
}