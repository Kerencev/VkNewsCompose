package com.kerencev.vknewscompose.presentation.screens.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kerencev.vknewscompose.presentation.navigation.AppNavGraph
import com.kerencev.vknewscompose.presentation.navigation.NavigationItem
import com.kerencev.vknewscompose.presentation.navigation.rememberNavigationState
import com.kerencev.vknewscompose.presentation.screens.comments.CommentsScreen
import com.kerencev.vknewscompose.presentation.screens.home.HomeScreen

@Composable
fun MainScreen() {
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
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsScreenContent = {
                HomeScreen(
                    paddingValues = paddingValues,
                    onCommentsClick = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            commentsScreenContent = { newsModel ->
                CommentsScreen(
                    newsModel = newsModel,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },
            favouriteScreenContent = { Text(text = "favourite Screen") },
            profileScreenContent = { Text(text = "Profile Screen") }
        )
    }
}