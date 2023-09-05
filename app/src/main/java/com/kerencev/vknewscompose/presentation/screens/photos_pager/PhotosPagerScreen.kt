package com.kerencev.vknewscompose.presentation.screens.photos_pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.compose.SetupSystemBar
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.pager.HorizontalPagerIndicator
import com.kerencev.vknewscompose.presentation.screens.photos_pager.views.PhotosPagerItem
import com.kerencev.vknewscompose.presentation.screens.photos_pager.views.PhotosPagerToolbar
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerEvent
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerViewState
import com.kerencev.vknewscompose.presentation.common.views.loading.ShimmerImage
import kotlinx.coroutines.CoroutineScope

@Composable
fun PhotosPagerScreen(
    params: PhotosPagerParams,
    onDismiss: () -> Unit,
) {
    SetupSystemBar(
        isNavigationBarContrastEnforced = false,
        darkIcons = false
    )

    val scope = rememberCoroutineScope()
    val component = getApplicationComponent()
        .getPhotosPagerScreenComponentFactory()
        .create(params = params)
    val viewModel: PhotosPagerViewModel = viewModel(factory = component.getViewModelFactory())
    val sendEvent: (PhotosPagerEvent) -> Unit = rememberUnitParams { viewModel.send(it) }
    val state = viewModel.observedState.collectAsState()

    PhotosPagerScreenContent(
        currentState = state,
        photoType = params.photoType,
        selectedPhotoNumber = params.selectedPhotoNumber,
        onDismiss = onDismiss,
        scope = scope,
        sendEvent = sendEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosPagerScreenContent(
    currentState: State<PhotosPagerViewState>,
    photoType: PhotoType,
    selectedPhotoNumber: Int,
    onDismiss: () -> Unit,
    scope: CoroutineScope,
    sendEvent: (PhotosPagerEvent) -> Unit,
) {
    val state = currentState.value
    if (selectedPhotoNumber > state.photos.size || state.photos.size > state.photosTotalCount) return
    val pagerState = rememberPagerState(
        initialPage = selectedPhotoNumber,
        pageCount = { state.photos.size }
    )
    if (photoType == PhotoType.PROFILE) HandlePagesOver(
        pagerState = pagerState,
        onLastPage = {
            if (state.isPhotosOver) return@HandlePagesOver
            sendEvent(PhotosPagerEvent.GetProfilePhotos)
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            state = pagerState,
            key = { state.photos[it].id },
            pageSpacing = 16.dp
        ) { index ->
            val item = state.photos[index]
            if (item is PhotoModel) PhotosPagerItem(
                pagerState = pagerState,
                scope = scope,
                imageUrl = item.url,
                onDismiss = onDismiss
            )
            else ShimmerImage()
        }

        when (state.photosTotalCount) {
            1 -> Unit

            in 2..5 -> HorizontalPagerIndicator(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                currentPage = pagerState.currentPage,
                totalPages = state.photosTotalCount
            )

            else -> PhotosPagerToolbar(
                currentPage = pagerState.currentPage,
                totalPages = state.photosTotalCount,
                onBackPressed = onDismiss
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HandlePagesOver(
    pagerState: PagerState,
    onLastPage: () -> Unit
) {
    LaunchedEffect(key1 = pagerState.canScrollForward) {
        if (!pagerState.canScrollForward) onLastPage()
    }
}
