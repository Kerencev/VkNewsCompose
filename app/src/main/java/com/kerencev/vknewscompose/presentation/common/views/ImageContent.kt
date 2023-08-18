package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.presentation.model.ImageContentModelUi

@Composable
fun ImageContent(
    photos: List<ImageContentModelUi>,
    onImageClick: (index: Int) -> Unit,
) {
    when (photos.size) {
        0 -> Unit
        1 -> {
            AsyncShimmerImage(
                imageUrl = photos[0].url,
                shimmerHeight = photos[0].height.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { onImageClick(0) }
            )
        }

        2 -> {
            ImageContentTwo(
                firstImage = photos[0],
                secondImage = photos[1],
                onImageClick = { index ->
                    onImageClick(index)
                }
            )
        }

        3 -> {
            ImageContentThree(
                firstImage = photos[0],
                secondImage = photos[1],
                thirdImage = photos[2],
                onImageClick = { index ->
                    onImageClick(index)
                }
            )
        }

        4 -> {
            ImageContentFour(
                firstImage = photos[0],
                secondImage = photos[1],
                thirdImage = photos[2],
                fourthImage = photos[3],
                onImageClick = { index ->
                    onImageClick(index)
                }
            )
        }

        else -> {
            ImageContentFivePlus(
                photos = photos,
                onImageClick = { index ->
                    onImageClick(index)
                }
            )
        }
    }
}