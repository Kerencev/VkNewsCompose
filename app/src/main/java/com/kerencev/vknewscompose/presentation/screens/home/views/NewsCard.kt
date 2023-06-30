package com.kerencev.vknewscompose.presentation.screens.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.extensions.notNullOrEmptyOrBlank
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    newsModel: NewsModelUi,
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onLikesClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            NewsHeader(newsModel = newsModel)
            Spacer(modifier = Modifier.height(4.dp))
            if (newsModel.contentText.notNullOrEmptyOrBlank()) Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = newsModel.contentText
            )
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