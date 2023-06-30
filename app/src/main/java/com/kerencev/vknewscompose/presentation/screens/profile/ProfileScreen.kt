package com.kerencev.vknewscompose.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.common.views.CardTitle
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileFriends
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfilePhotosGrid

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)

    val profileState = viewModel.profileState.collectAsState(ScreenState.Loading)
    val photosState = viewModel.photosState.collectAsState(ScreenState.Loading)
    val wallState = viewModel.wallState.collectAsState(WallState(emptyList(), isLoading = true))

    ProfileScreenContent(
        profileState = profileState,
        photosState = photosState,
        wallState = wallState,
        paddingValues = paddingValues,
        onRetryClick = {
            viewModel.loadProfile()
            viewModel.loadProfilePhotos()
            viewModel.loadWall()
        },
        loadPhotos = viewModel::loadProfilePhotos,
        loadWall = viewModel::loadWall
    )
}

@Composable
fun ProfileScreenContent(
    profileState: State<ScreenState<ProfileModel>>,
    photosState: State<ScreenState<List<PhotoModel>>>,
    wallState: State<WallState>,
    paddingValues: PaddingValues,
    onRetryClick: () -> Unit,
    loadPhotos: () -> Unit,
    loadWall: () -> Unit
) {
    when (val state = profileState.value) {
        is ScreenState.Content -> Profile(
            paddingValues = paddingValues,
            model = state.data,
            photosState = photosState,
            wallState = wallState,
            loadPhotos = loadPhotos,
            loadWall = loadWall
        )

        is ScreenState.Empty -> Unit
        is ScreenState.Loading -> ProgressBarDefault(modifier = Modifier.fillMaxSize())
        is ScreenState.Error -> TextWithButton(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(id = R.string.something_went_wrong),
            onRetryClick = onRetryClick
        )
    }
}

@Composable
fun Profile(
    paddingValues: PaddingValues,
    model: ProfileModel,
    photosState: State<ScreenState<List<PhotoModel>>>,
    wallState: State<WallState>,
    loadPhotos: () -> Unit,
    loadWall: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.background_news))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { ProfileHeader(model = model) }
        item { ProfileFriends(model = model) }
        item { ProfilePhotosGrid(photosState = photosState, onRetryLoadPhotos = loadPhotos) }

        item {
            Card(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                CardTitle(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(id = R.string.wall)
                )
            }
        }

        itemsIndexed(wallState.value.items) { index, item ->
            NewsCard(
                modifier = Modifier.padding(bottom = 8.dp),
                shape = if (index == 0) RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ) else RoundedCornerShape(16.dp),
                newsModel = item,
                onCommentsClick = {},
                onLikesClick = {}
            )
        }
        item {
            when {
                wallState.value.isLoading -> ProgressBarDefault(modifier = Modifier.padding(16.dp))

                wallState.value.isError -> TextWithButton(
                    modifier = Modifier.padding(16.dp),
                    title = stringResource(id = R.string.load_data_error),
                    onRetryClick = loadWall
                )

                else -> SideEffect { loadWall() }
            }
        }
    }
}
