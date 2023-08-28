package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.profileScreenNavGraph(
    profileScreenContent: @Composable (userId: Long) -> Unit,
    profilePhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    navigation(
        startDestination = Screen.Profile.route,
        route = Screen.ProfileGraph.route
    ) {
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_USER_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val userId = it.arguments?.getLong(Screen.KEY_USER_ID)
                ?: error("Args for profile screen is null")
            profileScreenContent(userId)
        }
        composable(
            route = Screen.ProfilePhotos.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_USER_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val userId = it.arguments?.getLong(Screen.KEY_USER_ID)
                ?: error("Args for profile photos screen is null")
            profilePhotosScreenContent(userId)
        }
        composable(
            route = Screen.Friends.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_FRIENDS) {
                    type = NavType.LongType
                }
            )
        ) {
            val userId = it.arguments?.getLong(Screen.KEY_FRIENDS)
                ?: error("Args for friends screen is null")
            friendsScreenContent(userId)
        }
    }
}
