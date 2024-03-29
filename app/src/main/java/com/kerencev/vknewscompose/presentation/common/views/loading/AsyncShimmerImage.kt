package com.kerencev.vknewscompose.presentation.common.views.loading

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.kerencev.vknewscompose.R

@Composable
fun AsyncShimmerImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    shimmerHeight: Dp,
    error: @Composable () -> Unit = {},
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
        error = { error() }
    )
}