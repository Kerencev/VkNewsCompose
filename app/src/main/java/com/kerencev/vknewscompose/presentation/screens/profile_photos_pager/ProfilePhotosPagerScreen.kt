package com.kerencev.vknewscompose.presentation.screens.profile_photos_pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.compose.SetupStatusColors
import com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.flow.ProfilePhotosPagerViewState
import com.smarttoolfactory.zoom.ZoomableImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfilePhotosPagerScreen(
    viewModelFactory: ViewModelFactory,
    selectedPhotoNumber: Int,
    onDismiss: () -> Unit
) {
    SetupStatusColors(
        color = Color.Black,
        isAppearanceLightStatusBars = false,
    )

    val scope = rememberCoroutineScope()
    val viewModel: ProfilePhotosPagerViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()

    ProfilePhotosPagerScreenContent(
        state = state,
        selectedPhotoNumber = selectedPhotoNumber,
        onDismiss = onDismiss,
        scope = scope
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfilePhotosPagerScreenContent(
    state: State<ProfilePhotosPagerViewState>,
    selectedPhotoNumber: Int,
    onDismiss: () -> Unit,
    scope: CoroutineScope
) {
    when (val photosState = state.value.photosState) {
        is ContentState.Content -> {
            val photos = photosState.data
            val pagerState = rememberPagerState(initialPage = selectedPhotoNumber)

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                pageCount = photos.size,
                state = pagerState,
                key = { photos[it].id },
                pageSpacing = 16.dp
            ) { index ->
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photos[index].url)
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
                        ZoomableImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(270f),
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

        is ContentState.Loading -> {}
        is ContentState.Error -> {}
    }
}
