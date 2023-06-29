package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.views.CardTitle

@Composable
fun ProfileFriends(
    model: ProfileModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardTitle(
                modifier = Modifier.weight(1f),
                text = "${model.friendsCount} ${getFriendWord(model.friendsCount)}"
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