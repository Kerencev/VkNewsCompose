package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.SecondTitle
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun ProfileFriends(
    modifier: Modifier = Modifier,
    friendsCount: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "$friendsCount ${getFriendWord(friendsCount)}",
                style = SecondTitle
            )
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_people),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun getFriendWord(count: Int): String {
    return stringResource(
        id = when {
            count % 10 == 1 && count % 100 != 11 -> R.string.friend
            count % 10 in 2..4 && count % 100 !in 12..14 -> R.string.friends_a_little
            else -> R.string.friends_a_lot
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileFriendsPreview() {
    VkNewsComposeTheme() {
        ProfileFriends(friendsCount = 10)
    }
}