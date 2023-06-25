package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
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
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }
}