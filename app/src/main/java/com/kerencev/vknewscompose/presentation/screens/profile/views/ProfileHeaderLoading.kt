package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Card
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.LightGray
import com.kerencev.vknewscompose.ui.theme.Shapes
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileHeaderLoading(
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
                androidx.compose.material.Card(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(20.dp)
                        .width(200.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .background(LightGray)
                    )
                }
                androidx.compose.material.Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(20.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .background(LightGray)
                    )
                }
            }
        }

        Image(
            modifier = Modifier
                .alpha(avatarAlpha)
                .size(avatarSize)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                .align(Alignment.TopCenter)
                .shimmer()
                .background(LightGray),
            painter = painterResource(id = R.drawable.ic_people),
            contentDescription = null,
        )
    }
}