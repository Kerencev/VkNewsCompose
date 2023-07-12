package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R

@Composable
fun ProfileToolbar(
    title: String,
    modifier: Modifier = Modifier,
    userAvatarModel: Any? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.surface, CircleShape),
            model = userAvatarModel,
            contentDescription = stringResource(id = R.string.user_avatar),
        )

        Text(
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onPrimary
        )
    }
}