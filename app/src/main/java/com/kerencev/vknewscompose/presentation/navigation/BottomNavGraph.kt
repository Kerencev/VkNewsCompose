package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    profilePhotosScreenContent: @Composable () -> Unit,
    friendsScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph(
            newsScreenContent = newsScreenContent,
            commentsScreenContent = commentsScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        profileScreenNavGraph(
            profileScreenContent = profileScreenContent,
            profilePhotosScreenContent = profilePhotosScreenContent,
            friendsScreenContent = friendsScreenContent
        )
    }
}