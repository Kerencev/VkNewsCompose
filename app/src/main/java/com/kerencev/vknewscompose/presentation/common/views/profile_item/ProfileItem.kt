package com.kerencev.vknewscompose.presentation.common.views.profile_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.UserProfileModel

@Composable
fun ProfileItem(
    profile: Profile,
    onClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    when (profile) {
        is UserProfileModel -> UserItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            user = profile
        )

        is GroupProfileModel -> GroupItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            model = profile
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}