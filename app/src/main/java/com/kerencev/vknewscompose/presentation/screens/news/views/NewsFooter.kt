package com.kerencev.vknewscompose.presentation.screens.news.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.extensions.formatStatisticCount
import com.kerencev.vknewscompose.presentation.common.views.icon.IconWithText
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.ui.theme.DarkRed

@Composable
fun NewsFooter(
    newsModel: NewsModelUi,
    onCommentsClick: () -> Unit,
    onLikesClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconWithText(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_views_count,
            text = newsModel.viewsCount.formatStatisticCount(),
        )
        Row {
            IconWithText(
                iconRes = R.drawable.ic_share,
                text = newsModel.sharesCount.formatStatisticCount(),
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconWithText(
                modifier = Modifier.clickable { onCommentsClick() },
                iconRes = R.drawable.ic_comment,
                text = newsModel.commentsCount.formatStatisticCount(),
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconWithText(
                modifier = Modifier.clickable { onLikesClick() },
                iconRes = if (newsModel.isLiked) R.drawable.ic_like_fill else R.drawable.ic_like,
                text = newsModel.likesCount.formatStatisticCount(),
                tint = if (newsModel.isLiked) DarkRed else MaterialTheme.colors.onSecondary
            )
        }
    }
}