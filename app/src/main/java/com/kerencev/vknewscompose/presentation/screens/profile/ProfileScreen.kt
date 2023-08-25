package com.kerencev.vknewscompose.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.compose.StatusBarHeight
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.screens.home.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileAvatarBackground
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileFriends
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfilePhotos
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileToolbar
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileWallFooter
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileWallHeader
import com.kerencev.vknewscompose.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ProfileScreen(
    userId: Long,
    paddingValues: PaddingValues,
    onPhotoClick: (index: Int) -> Unit,
    onWallItemClick: (index: Int, itemId: Long) -> Unit,
    onShowAllPhotosClick: () -> Unit,
    onProfileRefreshError: (message: String) -> Unit,
    onLogoutClick: () -> Unit,
    onFriendsClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val component = getApplicationComponent()
        .getProfileScreenComponentFactory()
        .create(userId)
    val viewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(ProfileShot.None)
    val sendEvent: (ProfileEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    ProfileScreenContent(
        isCurrentUser = userId == ProfileViewModel.DEFAULT_USER_ID,
        currentState = state,
        currentShot = shot,
        paddingValues = paddingValues,
        sendEvent = sendEvent,
        onPhotoClick = onPhotoClick,
        onWallItemClick = onWallItemClick,
        onShowAllPhotosClick = onShowAllPhotosClick,
        onProfileRefreshError = onProfileRefreshError,
        onLogoutClick = onLogoutClick,
        onFriendsClick = onFriendsClick,
        onBackPressed = onBackPressed,
    )
}

@Composable
fun ProfileScreenContent(
    isCurrentUser: Boolean,
    currentState: State<ProfileViewState>,
    currentShot: State<ProfileShot>,
    paddingValues: PaddingValues,
    sendEvent: (ProfileEvent) -> Unit,
    onPhotoClick: (Int) -> Unit,
    onWallItemClick: (index: Int, itemId: Long) -> Unit,
    onShowAllPhotosClick: () -> Unit,
    onProfileRefreshError: (message: String) -> Unit,
    onLogoutClick: () -> Unit,
    onFriendsClick: () -> Unit,
    onBackPressed: () -> Unit,
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
    SwipeRefresh(
        state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
        onRefresh = { sendEvent(ProfileEvent.RefreshProfileData) },
    ) {
        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.background_news))
                .padding(paddingValues)
        ) {
            ProfileAvatarBackground(
                boxScope = this,
                alpha = state.blurBackgroundAlpha,
                backgroundModel = if (state.profileState is ContentState.Content)
                    state.profileState.data.avatarUrl else R.color.background_news
            )

            ProfileToolbar(
                isCurrentUser = isCurrentUser,
                state = state,
                boxScope = this,
                onRefreshClick = { sendEvent(ProfileEvent.RefreshProfileData) },
                onLogoutClick = onLogoutClick,
                onBackPressed = onBackPressed,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 54.dp + StatusBarHeight())
                    .fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ProfileHeader(
                        profileState = state.profileState,
                        avatarAlpha = state.avatarAlpha,
                        avatarSize = state.avatarSize.dp,
                        onRetryClick = { sendEvent(ProfileEvent.GetProfile) }
                    )
                }

                item {
                    ProfileFriends(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable { onFriendsClick() },
                        friendsCount = state.friendsCount
                    )
                }

                item {
                    ProfilePhotos(
                        modifier = Modifier.padding(top = 8.dp),
                        photos = state.photos,
                        photosTotalCount = state.photosTotalCount,
                        isPhotosLoading = state.isPhotosLoading,
                        photosErrorMessage = state.photosErrorMessage,
                        onPhotoClick = onPhotoClick,
                        onShowAllClick = onShowAllPhotosClick,
                        loadPhotos = { sendEvent(ProfileEvent.GetProfilePhotos) },
                    )
                }

                item {
                    ProfileWallHeader(modifier = Modifier.padding(top = 8.dp))
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
                        onLikesClick = {},
                        onImageClick = { imageIndex ->
                            onWallItemClick(imageIndex, item.id)
                        }
                    )
                }

                item {
                    ProfileWallFooter(
                        isWallLoading = state.isWallLoading,
                        errorMessage = state.wallErrorMessage,
                        isWallPostOver = state.isWallPostsOver,
                        onScrollToBottom = { sendEvent(ProfileEvent.GetWall) },
                        totalWallItemsCount = state.wallItems.size
                    )
                }
            }

            when (val shot = currentShot.value) {
                is ProfileShot.ShowErrorMessage -> {
                    onProfileRefreshError(shot.message)
                    sendEvent(ProfileEvent.OnProfileErrorInvoked)
                }

                is ProfileShot.None -> Unit
            }
        }
    }
}
