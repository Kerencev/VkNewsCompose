package com.kerencev.vknewscompose.presentation.screens.photos_pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.compose.SetupSystemBar
import com.kerencev.vknewscompose.presentation.common.views.HorizontalPagerIndicator
import com.kerencev.vknewscompose.presentation.common.views.PhotosPagerItem
import com.kerencev.vknewscompose.presentation.common.views.PhotosPagerToolbar
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerViewState
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
    val state = viewModel.observedState.collectAsState()

    ProfilePhotosPagerScreenContent(
        state = state,
        selectedPhotoNumber = params.selectedPhotoNumber,
        onDismiss = onDismiss,
        scope = scope,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfilePhotosPagerScreenContent(
    state: State<PhotosPagerViewState>,
    selectedPhotoNumber: Int,
    onDismiss: () -> Unit,
    scope: CoroutineScope,
) {
    when (val photosState = state.value.photosState) {
        is ContentState.Content -> {
            val photos = photosState.data
            val pagerState = rememberPagerState(
                initialPage = selectedPhotoNumber,
                pageCount = photos::size
            )

            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    state = pagerState,
                    key = { photos[it].id },
                    pageSpacing = 16.dp
                ) { index ->
                    PhotosPagerItem(
                        pagerState = pagerState,
                        scope = scope,
                        imageUrl = photos[index].url,
                        onDismiss = onDismiss
                    )
                }

                when (photos.size) {
                    1 -> Unit

                    in 2..5 -> HorizontalPagerIndicator(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        currentPage = pagerState.currentPage,
                        totalPages = photos.size
                    )

                    else -> PhotosPagerToolbar(
                        currentPage = pagerState.currentPage,
                        totalPages = photos.size,
                        onBackPressed = onDismiss
                    )
                }
            }
        }

        is ContentState.Loading -> {}
        is ContentState.Error -> {}
    }
}
