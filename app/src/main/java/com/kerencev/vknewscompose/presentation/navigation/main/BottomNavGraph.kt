package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.home.HomeScreen
import com.kerencev.vknewscompose.presentation.navigation.home.homeScreenNavGraph
import com.kerencev.vknewscompose.presentation.navigation.profile.profileScreenNavGraph
import com.kerencev.vknewscompose.presentation.navigation.search.searchScreenNavGraph
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable (from: NavigationItem, profileParams: ProfileParams) -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    profilePhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (from: NavigationItem, userId: Long) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreen.Graph.route
    ) {
        homeScreenNavGraph(
            newsScreenContent = homeScreenContent,
            commentsScreenContent = commentsScreenContent,
            profileScreenContent = { profileScreenContent(NavigationItem.Home, it) },
            groupPhotosScreenContent = profilePhotosScreenContent,
            friendsScreenContent = { friendsScreenContent(NavigationItem.Home, it) },
        )
        searchScreenNavGraph(
            searchScreenContent = searchScreenContent,
            profileScreenContent = { profileScreenContent(NavigationItem.Search, it) },
            profilePhotosScreenContent = profilePhotosScreenContent,
            friendsScreenContent = { friendsScreenContent(NavigationItem.Search, it) }
        )
        profileScreenNavGraph(
            profileScreenContent = { profileScreenContent(NavigationItem.Profile, it) },
            profilePhotosScreenContent = profilePhotosScreenContent,
            friendsScreenContent = { friendsScreenContent(NavigationItem.Profile, it) }
        )
    }
}