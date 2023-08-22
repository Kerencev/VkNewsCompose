package com.kerencev.vknewscompose.presentation.screens.friends.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.FriendModel
import com.kerencev.vknewscompose.ui.theme.Green

@Composable
fun FriendItem(
    friendModel: FriendModel,
    modifier: Modifier = Modifier
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
                model = friendModel.avatarUrl,
                contentDescription = null
            )
            if (friendModel.isOnlineMobile) {
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 4.dp)
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.ic_smartphone),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Green)
                )
            } else if (friendModel.isOnline) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Green)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${friendModel.firstName} ${friendModel.lastName}",
            fontSize = 16.sp,
            color = MaterialTheme.colors.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}