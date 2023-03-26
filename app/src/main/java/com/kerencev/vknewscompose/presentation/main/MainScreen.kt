package com.kerencev.vknewscompose.presentation.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kerencev.vknewscompose.navigation.AppNavGraph
import com.kerencev.vknewscompose.navigation.NavigationItem
import com.kerencev.vknewscompose.navigation.rememberNavigationState
import com.kerencev.vknewscompose.presentation.screens.home.HomeScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.News,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
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
                    viewModel = viewModel,
                    paddingValues = paddingValues
                )
            },
            favouriteScreenContent = { Text(text = "favourite Screen") },
            profileScreenContent = { Text(text = "Profile Screen") }
        )
    }
}