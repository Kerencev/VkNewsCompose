package com.kerencev.vknewscompose.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.compose.SetupStatusColors
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.CardTitle
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ShimmerDefault
import com.kerencev.vknewscompose.presentation.common.views.SnackBarWithAction
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileFriends
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeaderError
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeaderLoading
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfilePhotosGrid
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileToolbar
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    onPhotoClick: (Int) -> Unit,
    onShowAllPhotosClick: () -> Unit
) {
    SetupStatusColors(
        color = MaterialTheme.colors.surface,
        isAppearanceLightStatusBars = !isSystemInDarkTheme()
    )

    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(ProfileShot.None)
    val sendEvent: (ProfileEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    ProfileScreenContent(
        currentState = state,
        currentShot = shot,
        paddingValues = paddingValues,
        sendEvent = sendEvent,
        onPhotoClick = onPhotoClick,
        onShowAllPhotosClick = onShowAllPhotosClick
    )
}

@Composable
fun ProfileScreenContent(
    currentState: State<ProfileViewState>,
    currentShot: State<ProfileShot>,
    paddingValues: PaddingValues,
    sendEvent: (ProfileEvent) -> Unit,
    onPhotoClick: (Int) -> Unit,
    onShowAllPhotosClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .onEach { sendEvent(ProfileEvent.OnUserScroll(firstVisibleItem = it)) }
            .flowOn(Dispatchers.IO)
            .launchIn(coroutineScope)

        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .onEach { sendEvent(ProfileEvent.OnUserScroll(firstVisibleItemScrollOffset = it)) }
            .flowOn(Dispatchers.IO)
            .launchIn(coroutineScope)
    }

    val state = currentState.value
    val blurBackgroundSrc = if (state.profileState is ContentState.Content)
        state.profileState.data.avatarUrl else R.color.background_news
    val userAvatarSrc = if (state.profileState is ContentState.Content)
        state.profileState.data.avatarUrl else R.drawable.ic_people
    val toolbarTitle = if (state.profileState is ContentState.Content)
        "${state.profileState.data.name} ${state.profileState.data.lastName}"
    else stringResource(id = R.string.empty_data)

    SwipeRefresh(
        state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
        onRefresh = { sendEvent(ProfileEvent.RefreshProfileData) },
    ) {

        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.background_news))
                .padding(paddingValues)
        ) {
            AsyncImage(
                modifier = Modifier
                    .alpha(state.blurBackgroundAlpha)
                    .fillMaxWidth()
                    .height(120.dp)
                    .blur(
                        radius = 32.dp,
                        edgeTreatment = BlurredEdgeTreatment(null)
                    )
                    .scale(5.0f)
                    .align(Alignment.TopCenter),
                model = blurBackgroundSrc,
                contentDescription = null
            )

            ProfileToolbar(
                title = toolbarTitle,
                userAvatarModel = userAvatarSrc,
                modifier = Modifier.alpha(state.topBarAlpha)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 54.dp)
                    .fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val profileState = state.profileState
                val photosState = state.profilePhotosState
                val stubModifier = Modifier.height(220.dp)

                item {
                    when (profileState) {
                        is ContentState.Content -> {
                            ProfileHeader(
                                model = profileState.data,
                                avatarAlpha = state.avatarAlpha,
                                avatarSize = state.avatarSize.dp
                            )
                        }

                        is ContentState.Loading -> ProfileHeaderLoading(
                            avatarAlpha = state.avatarAlpha,
                            avatarSize = state.avatarSize.dp
                        )

                        is ContentState.Error -> ProfileHeaderError(
                            avatarAlpha = state.avatarAlpha,
                            avatarSize = state.avatarSize.dp,
                            onRetryClick = { sendEvent(ProfileEvent.GetProfile) })
                    }
                }

                item {
                    ProfileFriends(
                        modifier = Modifier.padding(top = 8.dp),
                        friendsCount = state.friendsCount
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
                                onPhotoClick = onPhotoClick,
                                onShowAllClick = onShowAllPhotosClick
                            )

                            is ContentState.Loading -> ShimmerDefault(modifier = stubModifier)
                            is ContentState.Error -> TextWithButton(
                                modifier = stubModifier,
                                title = stringResource(id = R.string.profile_photos_error),
                                onClick = { sendEvent(ProfileEvent.GetProfilePhotos) }
                            )
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
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                            text = stringResource(id = R.string.wall)
                        )
                    }
                }

                itemsIndexed(state.wallItems) { index, item ->
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
                        state.isWallLoading -> ProgressBarDefault(
                            modifier = Modifier.padding(
                                16.dp
                            )
                        )

                        state.wallErrorMessage != null -> TextWithButton(
                            modifier = Modifier.padding(16.dp),
                            title = stringResource(id = R.string.load_data_error),
                            onClick = { sendEvent(ProfileEvent.GetWall) }
                        )

                        state.isWallPostsOver -> Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(
                                id = R.string.set_count,
                                state.wallItems.size
                            ),
                            color = LightBlue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )

                        else -> SideEffect { sendEvent(ProfileEvent.GetWall) }
                    }
                }
            }

            when (val shot = currentShot.value) {
                is ProfileShot.ShowErrorMessage -> {
                    SnackBarWithAction(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomCenter),
                        text = stringResource(
                            id = R.string.set_error_cause,
                            shot.message
                        ),
                        actionLabel = stringResource(id = R.string.ok),
                        onClick = { sendEvent(ProfileEvent.HideErrorSnackBar) }
                    )
                }

                is ProfileShot.None -> Unit
            }
        }
    }
}
