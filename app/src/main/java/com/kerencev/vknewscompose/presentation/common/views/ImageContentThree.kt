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
fun ImageContentThree(
    firstImage: ImageContentModelUi,
    secondImage: ImageContentModelUi,
    thirdImage: ImageContentModelUi,
    onImageClick: (index: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AsyncShimmerImage(
            imageUrl = firstImage.url,
            shimmerHeight = firstImage.height.dp,
            modifier = Modifier
                .padding(end = 2.dp)
                .weight(0.6f)
                .clip(RoundedCornerShape(2.dp))
                .clickable { onImageClick(0) }
        )
        Column(
            modifier = Modifier
                .weight(0.4f)
        ) {
            AsyncShimmerImage(
                imageUrl = secondImage.url,
                shimmerHeight = secondImage.height.dp,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(1) }
            )
            AsyncShimmerImage(
                imageUrl = thirdImage.url,
                shimmerHeight = thirdImage.height.dp,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onImageClick(2) }
            )
        }
    }
}