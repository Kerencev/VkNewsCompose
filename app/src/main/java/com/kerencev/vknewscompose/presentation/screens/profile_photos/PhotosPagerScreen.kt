package com.kerencev.vknewscompose.presentation.screens.profile_photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.kerencev.vknewscompose.presentation.screens.profile_photos.flow.ProfilePhotosViewState
import com.smarttoolfactory.zoom.ZoomableImage
import com.smarttoolfactory.zoom.rememberZoomState

@Composable
fun PhotosPagerScreen(
    viewModelFactory: ViewModelFactory,
    selectedPhotoNumber: Int,
) {
    SetupStatusColors(
        color = Color.Black,
        isAppearanceLightStatusBars = false,
    )

    val viewModel: ProfilePhotosViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()

    PhotosSliderScreenContent(
        state = state,
        selectedPhotoNumber = selectedPhotoNumber,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosSliderScreenContent(
    state: State<ProfilePhotosViewState>,
    selectedPhotoNumber: Int,
) {

    when (val photosState = state.value.photosState) {
        is ContentState.Content -> {
            val photos = photosState.data
            val pagerState = rememberPagerState(initialPage = selectedPhotoNumber)
            var isZoomedIn by remember { mutableStateOf(false) }

            LaunchedEffect(pagerState.currentPage) { isZoomedIn = false }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                pageCount = photos.size,
                state = pagerState,
                key = { photos[it].id },
                userScrollEnabled = !isZoomedIn,
                pageSpacing = 16.dp
            ) { index ->
                val zoomState = rememberZoomState(limitPan = true)

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photos[index].url)
                        .size(Size.ORIGINAL)
                        .build()
                )
                val painterState = painter.state

                if (painterState is AsyncImagePainter.State.Success) {
                    val imageBitmap = painterState.result.drawable.toBitmap().asImageBitmap()
                    ZoomableImage(
                        modifier = Modifier.fillMaxSize(),
                        imageBitmap = imageBitmap,
                        zoomState = zoomState,
                        consume = false,
                        onGestureStart = { isZoomedIn = it.zoom > 1f }
                    )
                }
            }
        }

        is ContentState.Loading -> {}
        is ContentState.Error -> {}
    }
}
