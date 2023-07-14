package com.kerencev.vknewscompose.presentation.screens.home.views

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
import com.kerencev.vknewscompose.presentation.common.views.ShimmerDefault

@Composable
fun AsyncShimmerImage(
    imageUrl: String?,
    shimmerHeight: Dp,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        modifier = modifier,
        contentDescription = stringResource(id = R.string.post_content_image),
        contentScale = ContentScale.FillWidth,
        loading = { ShimmerDefault(modifier = Modifier.height(shimmerHeight)) }
    )
}