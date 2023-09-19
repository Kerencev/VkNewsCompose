package com.kerencev.vknewscompose.presentation.common.views.profile_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.domain.entities.LastSeen
import com.kerencev.vknewscompose.domain.entities.OnlineType
import com.kerencev.vknewscompose.domain.entities.Platform
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.views.status.LastSeenFriends
import com.kerencev.vknewscompose.presentation.common.views.status.UserStatusOnline
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: UserProfileModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(60.dp)) {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                model = user.avatarUrl,
                contentDescription = null
            )
            if (user.onlineType == OnlineType.ONLINE || user.onlineType == OnlineType.ONLINE_MOBILE) {
                UserStatusOnline(
                    boxScope = this,
                    platform = user.platform,
                    alignment = Alignment.BottomEnd,
                    paddingValues = PaddingValues(end = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = user.name,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (user.onlineType == OnlineType.OFFLINE) {
                LastSeenFriends(
                    lastSeen = user.lastSeen,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    VkNewsComposeTheme {
        UserItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            user = UserProfileModel(
                id = 0,
                name = "Василий Пупкин",
                avatarUrl = null,
                coverUrl = null,
                lastName = "Пупкин",
                city = "Москва",
                universityName = "МГУ",
                friendsCount = 10,
                onlineType = OnlineType.OFFLINE,
                lastSeen = LastSeen(days = 5),
                platform = Platform.ANDROID
            )
        )
    }
}