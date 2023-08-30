package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import coil.request.ImageRequest
import com.kerencev.vknewscompose.R

@Composable
fun AsyncShimmerImage(
    imageUrl: String?,
    shimmerHeight: Dp,
    modifier: Modifier = Modifier,
    error: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        modifier = modifier,
        contentDescription = stringResource(id = R.string.post_content_image),
        contentScale = ContentScale.Crop,
        loading = { ShimmerDefault(modifier = Modifier.height(shimmerHeight)) },
        error = error
    )
}