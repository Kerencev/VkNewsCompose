package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    recommendationScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable (profileParams: ProfileParams) -> Unit,
    userProfileFromSuggestedScreenContent: @Composable (profileParams: ProfileParams) -> Unit,
    groupProfileScreenContent: @Composable (profileParams: ProfileParams) -> Unit,
    userPhotosScreenContent: @Composable (userId: Long) -> Unit,
    userPhotosFromSuggestedScreenContent: @Composable (userId: Long) -> Unit,
    groupPhotosScreenContent: @Composable (groupId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
    friendsFromSuggestedScreenContent: @Composable (userId: Long) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeGraph.route
    ) {
        homeScreenNavGraph(
            newsScreenContent = newsScreenContent,
            commentsScreenContent = commentsScreenContent,
            groupProfileScreenContent = groupProfileScreenContent,
            groupPhotosScreenContent = groupPhotosScreenContent,
            userProfileScreenContent = userProfileFromSuggestedScreenContent,
            userPhotosScreenContent = userPhotosFromSuggestedScreenContent,
            friendsScreenContent = friendsFromSuggestedScreenContent,
        )
        recommendationScreenNavGraph(
            recommendationScreenContent = recommendationScreenContent,
        )
        profileScreenNavGraph(
            profileScreenContent = userProfileScreenContent,
            profilePhotosScreenContent = userPhotosScreenContent,
            friendsScreenContent = friendsScreenContent
        )
    }
}