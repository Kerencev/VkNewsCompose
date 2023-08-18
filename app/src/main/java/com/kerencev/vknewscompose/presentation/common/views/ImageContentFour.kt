package com.kerencev.vknewscompose.presentation.common.views

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
import com.kerencev.vknewscompose.presentation.model.ImageContentModelUi

@Composable
fun ImageContentFour(
    firstImage: ImageContentModelUi,
    secondImage: ImageContentModelUi,
    thirdImage: ImageContentModelUi,
    fourthImage: ImageContentModelUi,
    onImageClick: (index: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .weight(1f)
        ) {
            AsyncShimmerImage(
                imageUrl = firstImage.url,
                shimmerHeight = firstImage.height.dp,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(0) }
            )
            AsyncShimmerImage(
                imageUrl = secondImage.url,
                shimmerHeight = secondImage.height.dp,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(1) }
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            AsyncShimmerImage(
                imageUrl = thirdImage.url,
                shimmerHeight = thirdImage.height.dp,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(2) }
            )
            AsyncShimmerImage(
                imageUrl = fourthImage.url,
                shimmerHeight = fourthImage.height.dp,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(3) }
            )
        }
    }
}