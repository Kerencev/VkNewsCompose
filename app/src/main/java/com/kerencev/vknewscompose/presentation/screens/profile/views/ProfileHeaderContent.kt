package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.views.IconWithText
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfileHeaderContent(
    model: ProfileModel,
    avatarAlpha: Float,
    avatarSize: Dp
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
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "${model.name} ${model.lastName}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    if (!model.city.isNullOrBlank()) {
                        IconWithText(
                            modifier = Modifier.weight(1f),
                            text = model.city,
                            iconRes = R.drawable.ic_location,
                            horizontalArrangement = Arrangement.Center
                        )
                    }
                    if (!model.universityName.isNullOrBlank()) {
                        IconWithText(
                            modifier = Modifier.weight(1f),
                            text = model.universityName,
                            iconRes = R.drawable.ic_hat_education,
                            horizontalArrangement = Arrangement.Center
                        )
                    }
                }
            }
        }

        AsyncImage(
            modifier = Modifier
                .alpha(avatarAlpha)
                .size(avatarSize)
                .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                .clip(CircleShape)
                .align(Alignment.TopCenter),
            model = model.avatarUrl,
            contentDescription = stringResource(id = R.string.user_avatar),
        )

    }
}