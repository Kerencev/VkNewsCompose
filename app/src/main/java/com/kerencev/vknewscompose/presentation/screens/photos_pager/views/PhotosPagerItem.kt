package com.kerencev.vknewscompose.presentation.screens.photos_pager.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.smarttoolfactory.zoom.ZoomableImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PhotosPagerItem(
    pagerState: PagerState,
    scope: CoroutineScope,
    imageUrl: String,
    onDismiss: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val painterState = painter.state

    if (painterState is AsyncImagePainter.State.Success) {
        val imageBitmap = painterState.result.drawable.toBitmap().asImageBitmap()
        val dismissState = rememberDismissState()

        if (dismissState.isDismissed(DismissDirection.StartToEnd) ||
            dismissState.isDismissed(DismissDirection.EndToStart)
        ) onDismiss()

        SwipeToDismiss(
            modifier = Modifier.rotate(90f),
            directions = setOf(
                DismissDirection.EndToStart,
                DismissDirection.StartToEnd
            ),
            state = dismissState,
            background = {},
            dismissThresholds = { FractionalThreshold(0.6f) }
        ) {
            Box(modifier = Modifier.rotate(270f)) {
                ZoomableImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageBitmap = imageBitmap,
                    consume = false,
                    onGesture = { data ->
                        scope.launch {
                            if (data.zoom > 1f) pagerState.stopScroll(MutatePriority.PreventUserInput)
                        }
                    },
                )
            }
        }
    }
}