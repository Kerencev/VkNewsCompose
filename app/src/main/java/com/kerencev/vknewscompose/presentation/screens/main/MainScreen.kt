package com.kerencev.vknewscompose.presentation.screens.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.activity.MainViewModel
import com.kerencev.vknewscompose.presentation.common.compose.SetupSystemBar
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.navigation.BottomNavGraph
import com.kerencev.vknewscompose.presentation.navigation.NavigationItem
import com.kerencev.vknewscompose.presentation.navigation.rememberNavigationState
import com.kerencev.vknewscompose.presentation.screens.comments.CommentsScreen
import com.kerencev.vknewscompose.presentation.screens.friends.FriendsScreen
import com.kerencev.vknewscompose.presentation.screens.home.HomeScreen
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainEvent
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainShot
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileScreen
import com.kerencev.vknewscompose.presentation.screens.profile_photos.ProfilePhotosScreen
import com.kerencev.vknewscompose.presentation.screens.search.SearchScreen
import com.kerencev.vknewscompose.ui.theme.LightBlue
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModelFactory: ViewModelFactory,
    mainViewModel: MainViewModel,
    onPhotoClick: (
        userId: Long?,
        type: PhotoType,
        index: Int,
        newsModelId: Long?
    ) -> Unit
) {
    SetupSystemBar()

    val navigationState = rememberNavigationState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val shot = mainViewModel.observedShot.collectAsState(initial = MainShot.None).value
    val sendEvent: (MainEvent) -> Unit = rememberUnitParams { mainViewModel.send(it) }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .height(56.dp)
                    .windowInsetsPadding(
                        WindowInsets.systemBars
                            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                    )
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Recommendation,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(text = item.title) },
                        selectedContentColor = LightBlue,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        //TODO: get away from repetitive screens
        BottomNavGraph(
            navHostController = navigationState.navHostController,
            newsScreenContent = {
                HomeScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = paddingValues,
                    onCommentsClick = { navigationState.navigateToComments(it) },
                    onError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onImageClick = { index, newsModelId ->
                        onPhotoClick(null, PhotoType.NEWS, index, newsModelId)
                    },
                    onHeaderClick = { profileId ->
                        navigationState.navigateToGroupProfile(profileId = profileId)
                    },
                    onSuggestedClick = { profileParams ->
                        navigationState.navigateToProfile(profileParams = profileParams)
                    }
                )
            },
            commentsScreenContent = { newsModel ->
                CommentsScreen(
                    newsModel = newsModel,
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            recommendationScreenContent = {
                SearchScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = paddingValues,
                    onItemClick = { profileParams ->
                        navigationState.navigateToProfile(profileParams = profileParams)
                    }
                )
            },
            userProfileScreenContent = { params ->
                ProfileScreen(
                    profileParams = params,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(params.id, PhotoType.PROFILE, index, null)
                    },
                    onWallItemClick = { index, itemId ->
                        onPhotoClick(params.id, PhotoType.WALL, index, itemId)
                    },
                    onShowAllPhotosClick = { navigationState.navigateToProfilePhotos(params.id) },
                    onProfileRefreshError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onLogoutClick = { sendEvent(MainEvent.Logout) },
                    onFriendsClick = { navigationState.navigateToFriends(params.id) },
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                )
            },
            userProfileFromSuggestedScreenContent = { params ->
                ProfileScreen(
                    profileParams = params,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(params.id, PhotoType.PROFILE, index, null)
                    },
                    onWallItemClick = { index, itemId ->
                        onPhotoClick(params.id, PhotoType.WALL, index, itemId)
                    },
                    onShowAllPhotosClick = {
                        navigationState.navigateToProfilePhotosFromSuggested(params.id)
                    },
                    onProfileRefreshError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onLogoutClick = { sendEvent(MainEvent.Logout) },
                    onFriendsClick = { navigationState.navigateToFriendsFromSuggested(params.id) },
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                )
            },
            groupProfileScreenContent = { params ->
                ProfileScreen(
                    profileParams = params,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(params.id, PhotoType.PROFILE, index, null)
                    },
                    onWallItemClick = { index, itemId ->
                        onPhotoClick(params.id, PhotoType.WALL, index, itemId)
                    },
                    onShowAllPhotosClick = { navigationState.navigateToGroupPhotos(params.id) },
                    onProfileRefreshError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                )
            },
            userPhotosScreenContent = { userId ->
                ProfilePhotosScreen(
                    userId = userId,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(userId, PhotoType.PROFILE, index, null)
                    },
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            userPhotosFromSuggestedScreenContent = { userId ->
                ProfilePhotosScreen(
                    userId = userId,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(userId, PhotoType.PROFILE, index, null)
                    },
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            groupPhotosScreenContent = { groupId ->
                ProfilePhotosScreen(
                    userId = groupId,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(groupId, PhotoType.PROFILE, index, null)
                    },
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            friendsScreenContent = { userId ->
                FriendsScreen(
                    paddingValues = paddingValues,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    onFriendClick = { profileId ->
                        navigationState.navigateToUserProfile(profileId = profileId)
                    },
                    userId = userId
                )
            },
            friendsFromSuggestedScreenContent = { userId ->
                FriendsScreen(
                    paddingValues = paddingValues,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    onFriendClick = { profileId ->
                        navigationState.navigateToProfile(
                            profileParams = ProfileParams(
                                id = profileId,
                                type = ProfileType.USER
                            )
                        )
                    },
                    userId = userId
                )
            }
        )
    }

    when (shot) {
        is MainShot.ShowErrorMessage -> {
            val message = stringResource(id = R.string.set_error_cause, shot.message)
            val actionLabel = stringResource(id = R.string.ok)
            LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    sendEvent(MainEvent.OnSnackBarDismiss)
                }
            }
        }

        is MainShot.None -> Unit
    }
}