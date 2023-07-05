package com.kerencev.vknewscompose.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.CardTitle
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ShimmerDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileFriends
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfilePhotosGrid
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    onPhotoClick: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()

    val sendEvent: (ProfileEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    ProfileScreenContent(
        state = state,
        paddingValues = paddingValues,
        sendEvent = sendEvent,
        onPhotoClick = onPhotoClick
    )
}

@Composable
fun ProfileScreenContent(
    state: State<ProfileViewState>,
    paddingValues: PaddingValues,
    sendEvent: (ProfileEvent) -> Unit,
    onPhotoClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.background_news))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currentState = state.value
        val profileState = currentState.profileState
        val photosState = currentState.photosState

        val stubModifier = Modifier.height(220.dp)

        item {
            Card(shape = Shapes.large) {
                when (profileState) {
                    is ContentState.Content -> ProfileHeader(model = profileState.data)
                    is ContentState.Loading -> ShimmerDefault(modifier = stubModifier)
                    is ContentState.Error -> TextWithButton(
                        modifier = stubModifier,
                        title = stringResource(id = R.string.profile_error),
                        onClick = { sendEvent(ProfileEvent.GetProfile) })
                }
            }
        }

        item {
            ProfileFriends(
                modifier = Modifier.padding(top = 8.dp),
                friendsCount = state.value.friendsCount
            )
        }

        item {
            Card(
                modifier = Modifier.padding(top = 8.dp),
                shape = Shapes.large
            ) {
                when (photosState) {
                    is ContentState.Content -> ProfilePhotosGrid(
                        photos = photosState.data,
                        onPhotoClick = onPhotoClick
                    )

                    is ContentState.Loading -> ShimmerDefault(modifier = stubModifier)
                    is ContentState.Error -> TextWithButton(
                        modifier = stubModifier,
                        title = stringResource(id = R.string.profile_photos_error),
                        onClick = { sendEvent(ProfileEvent.GetPhotos) })
                }
            }
        }

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

        itemsIndexed(currentState.wallItems) { index, item ->
            NewsCard(
                modifier = Modifier.padding(bottom = 8.dp),
                shape = if (index == 0) RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ) else Shapes.large,
                newsModel = item,
                onCommentsClick = {},
                onLikesClick = {}
            )
        }
        item {
            when {
                currentState.isWallLoading -> ProgressBarDefault(modifier = Modifier.padding(16.dp))

                currentState.wallErrorMessage != null -> TextWithButton(
                    modifier = Modifier.padding(16.dp),
                    title = stringResource(id = R.string.load_data_error),
                    onClick = { sendEvent(ProfileEvent.GetWall) }
                )

                currentState.isWallPostsOver -> Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.set_count, currentState.wallItems.size),
                    color = LightBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                else -> SideEffect { sendEvent(ProfileEvent.GetWall) }
            }
        }
    }
}
