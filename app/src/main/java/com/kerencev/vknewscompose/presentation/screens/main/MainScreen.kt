package com.kerencev.vknewscompose.presentation.screens.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
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
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileScreen
import com.kerencev.vknewscompose.presentation.screens.profile_photos.ProfilePhotosScreen
import com.kerencev.vknewscompose.ui.theme.LightBlue
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    viewModelFactory: ViewModelFactory,
    onPhotoClick: (type: PhotoType, index: Int, newsModelId: Long) -> Unit
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
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                )
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
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
        BottomNavGraph(
            navHostController = navigationState.navHostController,
            newsScreenContent = {
                HomeScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = paddingValues,
                    onCommentsClick = { navigationState.navigateToComments(it) },
                    onError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onImageClick = { index, newsModelId ->
                        onPhotoClick(PhotoType.NEWS, index, newsModelId)
                    }
                )
            },
            commentsScreenContent = { newsModel ->
                CommentsScreen(
                    newsModel = newsModel,
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            favouriteScreenContent = { Text(text = "favourite Screen") },
            profileScreenContent = { userId ->
                ProfileScreen(
                    userId = userId,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(PhotoType.PROFILE, index, 0)
                    },
                    onWallItemClick = { index, itemId ->
                        onPhotoClick(PhotoType.WALL, index, itemId)
                    },
                    onShowAllPhotosClick = { navigationState.navigateToProfilePhotos(userId) },
                    onProfileRefreshError = { sendEvent(MainEvent.ShowErrorMessage(it)) },
                    onLogoutClick = { sendEvent(MainEvent.Logout) },
                    onFriendsClick = { navigationState.navigateToFriends(userId) },
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                )
            },
            profilePhotosScreenContent = { userId ->
                ProfilePhotosScreen(
                    userId = userId,
                    paddingValues = paddingValues,
                    onPhotoClick = { index ->
                        onPhotoClick(PhotoType.PROFILE, index, 0)
                    },
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            friendsScreenContent = { userId ->
                FriendsScreen(
                    paddingValues = paddingValues,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    onFriendClick = { navigationState.navigateToProfile(it) },
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