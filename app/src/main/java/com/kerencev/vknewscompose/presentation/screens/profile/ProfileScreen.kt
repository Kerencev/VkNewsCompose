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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.app.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.compose.statusBarHeight
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.news.views.NewsCard
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotoType
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerParams
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileFriends
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfilePhotos
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileWallFooter
import com.kerencev.vknewscompose.presentation.screens.profile.views.ProfileWallHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.cover.ProfileCover
import com.kerencev.vknewscompose.presentation.screens.profile.views.header.ProfileHeader
import com.kerencev.vknewscompose.presentation.screens.profile.views.toolbar.ProfileToolbar
import com.kerencev.vknewscompose.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ProfileScreen(
    profileParams: ProfileParams,
    paddingValues: PaddingValues,
    onPhotoClick: (params: PhotosPagerParams) -> Unit,
    onWallItemClick: (params: PhotosPagerParams) -> Unit,
    onShowAllPhotosClick: () -> Unit,
    onProfileRefreshError: (message: String) -> Unit,
    onBackPressed: () -> Unit,
    onLogoutClick: () -> Unit = {},
    onFriendsClick: () -> Unit = {},
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    showSnackBar: (message: String) -> Unit,
) {
    val component = getApplicationComponent()
        .getProfileScreenComponentFactory()
        .create(profileParams)
    val viewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(ProfileShot.None)
    val sendEvent: (ProfileEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    ProfileScreenContent(
        profileType = profileParams.type,
        currentState = state,
        currentShot = shot,
        paddingValues = paddingValues,
        sendEvent = sendEvent,
        onPhotoClick = { index ->
            onPhotoClick(
                PhotosPagerParams(
                    userId = profileParams.id,
                    photoType = PhotoType.PROFILE,
                    selectedPhotoNumber = index
                )
            )
        },
        onWallItemClick = { index, itemId ->
            onWallItemClick(
                PhotosPagerParams(
                    userId = profileParams.id,
                    photoType = PhotoType.WALL,
                    selectedPhotoNumber = index,
                    newsModelId = itemId
                )
            )
        },
        onShowAllPhotosClick = onShowAllPhotosClick,
        onProfileRefreshError = onProfileRefreshError,
        onLogoutClick = onLogoutClick,
        onFriendsClick = onFriendsClick,
        onCommentsClick = onCommentsClick,
        onBackPressed = onBackPressed,
        showSnackBar = showSnackBar,
    )
}

@Composable
fun ProfileScreenContent(
    profileType: ProfileType,
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
    onCommentsClick: (newsModel: NewsModelUi) -> Unit,
    onBackPressed: () -> Unit,
    showSnackBar: (message: String) -> Unit,
) {
    val noFeatureMessage = stringResource(id = R.string.no_feature_message)
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
            ProfileCover(
                boxScope = this,
                alpha = state.blurBackgroundAlpha,
                profileState = state.profileState,
                profileType = profileType
            )

            ProfileToolbar(
                state = state,
                boxScope = this,
                onRefreshClick = { sendEvent(ProfileEvent.RefreshProfileData) },
                onLogoutClick = onLogoutClick,
                onBackPressed = onBackPressed,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 54.dp + statusBarHeight())
                    .fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ProfileHeader(
                        profileType = profileType,
                        profileState = state.profileState,
                        avatarAlpha = state.avatarAlpha,
                        avatarSize = state.avatarSize.dp,
                        onRetryClick = { sendEvent(ProfileEvent.GetProfile) }
                    )
                }

                if (profileType == ProfileType.USER) item {
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
                        totalCount = state.photosTotalCount,
                        isLoading = state.isPhotosLoading,
                        errorMessage = state.photosErrorMessage,
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
                        onCommentsClick = { onCommentsClick(item) },
                        onLikesClick = { sendEvent(ProfileEvent.ChangeLikeStatus(item)) },
                        onImageClick = { imageIndex ->
                            onWallItemClick(imageIndex, item.id)
                        },
                        onIconMoreClick = { showSnackBar(noFeatureMessage) },
                        onShareClick = { showSnackBar(noFeatureMessage) },
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
