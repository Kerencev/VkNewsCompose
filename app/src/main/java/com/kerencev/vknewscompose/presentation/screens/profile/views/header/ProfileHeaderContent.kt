package com.kerencev.vknewscompose.presentation.screens.profile.views.header

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.LastSeen
import com.kerencev.vknewscompose.domain.entities.OnlineType
import com.kerencev.vknewscompose.domain.entities.Platform
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.views.icon.IconWithText
import com.kerencev.vknewscompose.presentation.common.views.status.UserStatus
import com.kerencev.vknewscompose.ui.theme.Shapes
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun ProfileHeaderContent(
    profile: Profile,
    avatarAlpha: Float,
    avatarSize: Dp,
    contentPadding: PaddingValues,
    namePadding: PaddingValues,
    contentAlignment: Alignment.Horizontal,
    avatarPadding: PaddingValues,
    avatarAlign: Alignment,
) {
    Box {
        Card(
            modifier = Modifier.padding(contentPadding),
            shape = Shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = contentAlignment
            ) {
                Text(
                    modifier = Modifier.padding(namePadding),
                    text = profile.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                if (profile is UserProfileModel) {
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
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
            }
        }

        AsyncImage(
            modifier = Modifier
                .padding(avatarPadding)
                .alpha(avatarAlpha)
                .size(avatarSize)
                .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                .clip(CircleShape)
                .align(avatarAlign),
            model = profile.avatarUrl,
            contentDescription = stringResource(id = R.string.user_avatar),
        )

        if (profile is UserProfileModel) {
            UserStatus(
                boxScope = this,
                onlineType = profile.onlineType,
                lastSeen = profile.lastSeen,
                platform = profile.platform,
                alignment = avatarAlign,
                alpha = avatarAlpha,
                paddingValues = getOnlinePadding(profile.onlineType, avatarSize)
            )
        }
    }
}

private fun getOnlinePadding(onlineType: OnlineType, avatarSize: Dp): PaddingValues {
    return if (onlineType == OnlineType.ONLINE)
        PaddingValues(top = avatarSize - (avatarSize / 3), start = (avatarSize))
    else
        PaddingValues(
            top = avatarSize - (avatarSize / 3),
            start = (avatarSize - (avatarSize / 7))
        )
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    VkNewsComposeTheme {
        ProfileHeaderContent(
            profile = UserProfileModel(
                id = 0,
                name = "Василий Пупкин",
                avatarUrl = null,
                coverUrl = null,
                lastName = "Пупкин",
                city = "Москва",
                universityName = "МГУ",
                friendsCount = 10,
                onlineType = OnlineType.ONLINE,
                lastSeen = LastSeen(days = 5),
                platform = Platform.WINDOWS_10
            ),
            avatarAlpha = 1f,
            avatarSize = 100.dp,
            contentPadding = PaddingValues(top = 50.dp),
            namePadding = PaddingValues(top = 8.dp),
            contentAlignment = Alignment.CenterHorizontally,
            avatarPadding = PaddingValues(),
            avatarAlign = Alignment.TopCenter,
        )
    }
}