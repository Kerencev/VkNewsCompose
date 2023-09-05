package com.kerencev.vknewscompose.presentation.common.views.image_content

import androidx.compose.foundation.clickable
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
fun ImageContentTwo(
    firstImage: ImageContentModelUi,
    secondImage: ImageContentModelUi,
    onImageClick: (index: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
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
}