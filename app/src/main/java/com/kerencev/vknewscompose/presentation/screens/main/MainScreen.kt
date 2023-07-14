package com.kerencev.vknewscompose.presentation.screens.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.navigation.BottomNavGraph
import com.kerencev.vknewscompose.presentation.navigation.NavigationItem
import com.kerencev.vknewscompose.presentation.navigation.rememberNavigationState
import com.kerencev.vknewscompose.presentation.screens.comments.CommentsScreen
import com.kerencev.vknewscompose.presentation.screens.home.HomeScreen
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileScreen
import com.kerencev.vknewscompose.presentation.screens.profile_photos.ProfilePhotosScreen
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun MainScreen(
    viewModelFactory: ViewModelFactory,
    onPhotoClick: (index: Int) -> Unit
) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
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
                    onCommentsClick = { navigationState.navigateToComments(it) }
                )
            },
            commentsScreenContent = { newsModel ->
                CommentsScreen(
                    newsModel = newsModel,
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            favouriteScreenContent = { Text(text = "favourite Screen") },
            profileScreenContent = {
                ProfileScreen(
                    paddingValues = paddingValues,
                    viewModelFactory = viewModelFactory,
                    onPhotoClick = onPhotoClick,
                    onShowAllPhotosClick = { navigationState.navigateToProfilePhotos() }
                )
            },
            profilePhotosScreenContent = {
                ProfilePhotosScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = paddingValues,
                    onPhotoClick = onPhotoClick,
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            }
        )
    }
}