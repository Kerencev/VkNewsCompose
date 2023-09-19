package com.kerencev.vknewscompose.presentation.screens.news.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.views.image_content.ImageContent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    newsModel: NewsModelUi,
    onCommentsClick: () -> Unit,
    onLikesClick: () -> Unit,
    onImageClick: (index: Int) -> Unit,
    onHeaderClick: () -> Unit = {},
    onIconMoreClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Box(modifier = modifier.background(color = colorResource(id = R.color.background_news))) {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                NewsHeader(
                    modifier = Modifier.clickable { onHeaderClick() },
                    newsModel = newsModel,
                    onIconMoreClick = onIconMoreClick
                )
                Spacer(modifier = Modifier.height(4.dp))
                NewsText(text = newsModel.contentText)
                Spacer(modifier = Modifier.height(4.dp))
                ImageContent(
                    photos = newsModel.imageContent,
                    onImageClick = onImageClick
                )
                Spacer(modifier = Modifier.height(8.dp))
                NewsFooter(
                    newsModel = newsModel,
                    onCommentsClick = onCommentsClick,
                    onLikesClick = onLikesClick,
                    onShareClick = onShareClick
                )
            }
        }
    }
}
