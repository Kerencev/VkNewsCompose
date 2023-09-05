package com.kerencev.vknewscompose.presentation.common.views.profile_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.extensions.formatStatisticCount
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    model: GroupProfileModel
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
                model = model.avatarUrl,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = model.name,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_people),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = model.memberCount.formatStatisticCount(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupItemPreview() {
    VkNewsComposeTheme {
        GroupItem(
            model = GroupProfileModel(
                id = 0,
                name = "Очень длинное название группы",
                avatarUrl = null,
                coverUrl = null,
                memberCount = 375645,
                description = "Описание группы вот такое вот авотова вовоыа вавоыао выа овы ао выо ао выоа овы ао выао овыа о авооыва "
            )
        )
    }
}