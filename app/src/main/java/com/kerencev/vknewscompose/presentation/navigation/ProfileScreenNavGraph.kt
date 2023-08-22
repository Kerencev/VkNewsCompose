package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.profileScreenNavGraph(
    profileScreenContent: @Composable () -> Unit,
    profilePhotosScreenContent: @Composable () -> Unit,
    friendsScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.ProfileStart.route,
        route = Screen.Profile.route
    ) {
        composable(Screen.ProfileStart.route) {
            profileScreenContent()
        }
        composable(Screen.ProfilePhotos.route) {
            profilePhotosScreenContent()
        }
        composable(Screen.Friends.route) {
            friendsScreenContent()
        }
    }
}
