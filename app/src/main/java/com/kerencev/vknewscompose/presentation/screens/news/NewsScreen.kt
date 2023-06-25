package com.kerencev.vknewscompose.presentation.screens.news

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.extensions.formatStatisticCount
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.home.HomeViewModel
import com.kerencev.vknewscompose.ui.theme.DarkBlue
import com.kerencev.vknewscompose.ui.theme.DarkRed

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    viewModel: HomeViewModel,
    news: List<NewsModelUi>,
    paddingValues: PaddingValues,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    nextDataIsLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.background_news)),
        contentPadding = PaddingValues(
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news, { it.id }) { newsItem ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.onNewsItemDismiss(newsModelUi = newsItem)
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
                    onCommentsClick = onCommentsClick,
                    onLikesClick = {
                        viewModel.changeLikesStatus(newsItem)
                    }
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextNews()
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    newsModel: NewsModelUi,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onLikesClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            NewsHeader(newsModel = newsModel)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsModel.contentText)
            Spacer(modifier = Modifier.height(4.dp))
            AsyncImage(
                model = newsModel.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = stringResource(id = R.string.post_content_image),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsFooter(newsModel = newsModel, onCommentsClick, onLikesClick)
        }
    }
}

@Composable
fun NewsHeader(newsModel: NewsModelUi) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = newsModel.communityImageUrl,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
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
                text = newsModel.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsModel.postTime,
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
    newsModel: NewsModelUi,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onLikesClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(
                modifier = Modifier,
                iconRes = R.drawable.ic_views_count,
                text = newsModel.viewsCount.formatStatisticCount(),
            )
        }
        Row {
            IconWithText(
                modifier = Modifier,
                iconRes = R.drawable.ic_share,
                text = newsModel.sharesCount.formatStatisticCount(),
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconWithText(
                modifier = Modifier.clickable { onCommentsClick(newsModel) },
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

@Composable
fun IconWithText(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    text: String,
    tint: Color = MaterialTheme.colors.onSecondary
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = tint
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = text, color = MaterialTheme.colors.onSecondary)
    }
}