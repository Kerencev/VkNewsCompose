package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    newsScreenContent: @Composable () -> Unit,
    commentsScreenNewsContent: @Composable (NewsModelUi) -> Unit,
    commentsScreenRecommendationContent: @Composable (NewsModelUi) -> Unit,
    recommendationScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable (userId: Long) -> Unit,
    profilePhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeGraph.route
    ) {
        homeScreenNavGraph(
            newsScreenContent = newsScreenContent,
            commentsScreenContent = commentsScreenNewsContent
        )
        recommendationScreenNavGraph(
            recommendationScreenContent = recommendationScreenContent,
            commentsScreenContent = commentsScreenRecommendationContent
        )
        profileScreenNavGraph(
            profileScreenContent = profileScreenContent,
            profilePhotosScreenContent = profilePhotosScreenContent,
            friendsScreenContent = friendsScreenContent
        )
    }
}