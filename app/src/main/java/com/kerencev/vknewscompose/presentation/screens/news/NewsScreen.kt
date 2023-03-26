package com.kerencev.vknewscompose.presentation.screens.news

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.model.NewsModel
import com.kerencev.vknewscompose.extensions.toDateTime
import com.kerencev.vknewscompose.presentation.screens.home.HomeViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    viewModel: HomeViewModel,
    news: List<NewsModel>,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news, { it.id }) { newsItem ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.onNewsItemDismiss(newsModel = newsItem)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                directions = setOf(DismissDirection.EndToStart),
                state = dismissState,
                background = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .alpha(0.5f)
                            .background(Color.Red),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(id = R.string.delete),
                            fontSize = 24.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.White
                        )
                    }
                }
            ) {
                NewsCard(
                    newsModel = newsItem,
                    onStatisticClick = { statisticType ->
                        viewModel.onStatisticClick(newsItem, statisticType)
                    },
                    onCommentsClick = onCommentsClick
                )
            }
        }
    }
}

@Composable
fun NewsCard(
    newsModel: NewsModel,
    onStatisticClick: (type: NewsStatisticType) -> Unit,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            NewsHeader(newsModel = newsModel)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsModel.text)
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = stringResource(id = R.string.post_content_image),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsFooter(newsModel = newsModel, onStatisticClick, onCommentsClick)
        }
    }
}

@Composable
fun NewsHeader(newsModel: NewsModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(
                id = R.string.group_avatar
            )
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text(
                text = newsModel.name,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsModel.postTime.toDateTime(),
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.more),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun NewsFooter(
    newsModel: NewsModel,
    onStatisticClick: (type: NewsStatisticType) -> Unit,
    onCommentsClick: (newsModel: NewsModel) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(
                modifier = Modifier.clickable { onStatisticClick(NewsStatisticType.VIEWS) },
                iconRes = R.drawable.ic_views_count,
                text = newsModel.viewsCount.toString(),
            )
        }
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(
                modifier = Modifier.clickable { onStatisticClick(NewsStatisticType.SHARES) },
                iconRes = R.drawable.ic_share,
                text = newsModel.sharesCount.toString(),
            )
            IconWithText(
                modifier = Modifier.clickable { onCommentsClick(newsModel) },
                iconRes = R.drawable.ic_comment,
                text = newsModel.commentsCount.toString(),
            )
            IconWithText(
                modifier = Modifier.clickable { onStatisticClick(NewsStatisticType.LIKES) },
                iconRes = R.drawable.ic_like,
                text = newsModel.likesCount.toString(),
            )
        }
    }
}

@Composable
fun IconWithText(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = MaterialTheme.colors.onSecondary)
    }
}