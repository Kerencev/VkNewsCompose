package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfileHeaderError(
    avatarAlpha: Float,
    avatarSize: Dp,
    errorMessage: String = stringResource(id = R.string.profile_error),
    onRetryClick: () -> Unit
) {
    Box {
        Card(
            modifier = Modifier.padding(top = avatarSize / 2),
            shape = Shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextWithButton(
                    title = errorMessage,
                    modifier = Modifier.padding(8.dp),
                    onClick = onRetryClick
                )
            }
        }

        Image(
            modifier = Modifier
                .alpha(avatarAlpha)
                .size(avatarSize)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.ic_people),
            contentDescription = null,
        )
    }
}