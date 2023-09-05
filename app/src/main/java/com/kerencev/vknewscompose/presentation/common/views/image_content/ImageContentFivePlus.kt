package com.kerencev.vknewscompose.presentation.common.views.image_content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.presentation.common.views.loading.AsyncShimmerImage
import com.kerencev.vknewscompose.presentation.model.ImageContentModelUi

@Composable
fun ImageContentFivePlus(
    photos: List<ImageContentModelUi>,
    onImageClick: (index: Int) -> Unit,
) {
    val topRowWeight = if (photos.size < 10) photos.size / 10.0f else 0.9f
    val bottomRowWeight = 1f - topRowWeight
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .weight(topRowWeight)
        ) {
            AsyncShimmerImage(
                imageUrl = photos[0].url,
                shimmerHeight = photos[0].height.dp,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(0) }
            )
            AsyncShimmerImage(
                imageUrl = photos[1].url,
                shimmerHeight = photos[1].height.dp,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(1) }
            )
        }
        Row(
            modifier = Modifier
                .weight(bottomRowWeight)
        ) {
            for (i in 2 until photos.size) {
                val endPadding = if (i <= photos.size - 2) 2.dp else 0.dp
                AsyncShimmerImage(
                    imageUrl = photos[i].url,
                    shimmerHeight = photos[i].height.dp,
                    modifier = Modifier
                        .padding(end = endPadding)
                        .weight(1f)
                        .clip(RoundedCornerShape(2.dp))
                        .clickable { onImageClick(i) }
                )
            }
        }
    }
}