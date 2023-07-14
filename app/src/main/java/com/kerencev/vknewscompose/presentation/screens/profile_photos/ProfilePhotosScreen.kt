package com.kerencev.vknewscompose.presentation.screens.profile_photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.SetupStatusColors
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.screens.home.views.AsyncShimmerImage
import com.kerencev.vknewscompose.presentation.screens.profile_photos.flow.ProfilePhotosViewState
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun ProfilePhotosScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onPhotoClick: (index: Int) -> Unit,
    onBackPressed: () -> Unit,
) {
    SetupStatusColors(
        color = MaterialTheme.colors.surface,
        isAppearanceLightStatusBars = !isSystemInDarkTheme()
    )

    val viewModel: ProfilePhotosViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()

    ProfilePhotosScreenContent(
        state = state,
        paddingValues = paddingValues,
        onPhotoClick = onPhotoClick,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfilePhotosScreenContent(
    state: State<ProfilePhotosViewState>,
    paddingValues: PaddingValues,
    onPhotoClick: (index: Int) -> Unit,
    onBackPressed: () -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        paddingValues = paddingValues,
        toolBarTitle = {
            Text(
                text = stringResource(id = R.string.set_photos_count, state.value.photos.size),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
        },
        toolBarNavigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = LightBlue
                )
            }
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(100.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalItemSpacing = 2.dp,
        ) {
            itemsIndexed(state.value.photos) { index, item ->
                AsyncShimmerImage(
                    imageUrl = item.url,
                    shimmerHeight = item.height.dp,
                    modifier = Modifier
                        .width(200.dp)
                        .clickable { onPhotoClick(index) }
                )
            }
        }
    }
}
