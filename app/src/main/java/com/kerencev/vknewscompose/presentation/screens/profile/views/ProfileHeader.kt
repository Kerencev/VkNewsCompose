package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.views.IconWithText

@Composable
fun ProfileHeader(
    model: ProfileModel
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .blur(
                radius = 32.dp,
                edgeTreatment = BlurredEdgeTreatment(null)
            )
            .scale(3.0f)
            .alpha(0.5f),
        model = model.avatarUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = model.avatarUrl,
            modifier = Modifier
                .clip(CircleShape)
                .height(160.dp),
            contentDescription = stringResource(id = R.string.user_avatar)
        )
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
            IconWithText(
                modifier = Modifier.weight(1f),
                text = model.city.orEmpty(),
                iconRes = R.drawable.ic_location
            )
            IconWithText(
                modifier = Modifier.weight(1f),
                text = model.universityName.orEmpty(),
                iconRes = R.drawable.ic_hat_education
            )
        }
    }
}