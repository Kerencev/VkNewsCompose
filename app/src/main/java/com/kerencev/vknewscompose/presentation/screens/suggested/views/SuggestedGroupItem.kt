package com.kerencev.vknewscompose.presentation.screens.suggested.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.presentation.common.views.AsyncShimmerImage
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun SuggestedGroupItem(
    modifier: Modifier = Modifier,
    model: GroupProfileModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(100.dp)
                    .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape),
                model = model.avatarUrl,
                contentDescription = stringResource(id = R.string.user_avatar),
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = model.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(
                        id = R.string.set_member_count,
                        getMembers(model.memberCount)
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        AsyncShimmerImage(
            modifier = Modifier.alpha(0.5f),
            imageUrl = model.coverUrl,
            shimmerHeight = 200.dp
        )
    }
}

private fun getMembers(memberCount: Int): Float {
    //TODO: число знаков после зпт
    return memberCount.toFloat() / 1000F
}

@Preview(showBackground = true)
@Composable
fun SuggestedGroupItemPreview() {
    VkNewsComposeTheme {
        SuggestedGroupItem(
            model = GroupProfileModel(
                id = 0,
                name = "Очень длинное название группы врыаловыалотыволатловыата",
                avatarUrl = null,
                coverUrl = null,
                memberCount = 375645
            )
        )
    }
}