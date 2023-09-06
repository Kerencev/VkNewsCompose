package com.kerencev.vknewscompose.presentation.screens.profile.views.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.extensions.formatStatisticCount
import com.kerencev.vknewscompose.presentation.common.views.icon.IconWithText

@Composable
fun GroupProfileHeader(
    modifier: Modifier = Modifier,
    profile: GroupProfileModel
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(4.dp))
        IconWithText(
            iconRes = R.drawable.ic_people,
            text = stringResource(
                id = R.string.set_members,
                profile.memberCount.formatStatisticCount()
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profile.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onSecondary,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}