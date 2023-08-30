package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.presentation.model.ProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

fun NavGraphBuilder.profileScreenNavGraph(
    profileScreenContent: @Composable (params: ProfileParams) -> Unit,
    profilePhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    navigation(
        startDestination = Screen.UserProfile.route,
        route = Screen.ProfileGraph.route
    ) {
        composable(
            route = Screen.UserProfile.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PROFILE_ID) { type = NavType.LongType },
                navArgument(name = Screen.KEY_PROFILE_TYPE) { type = NavType.StringType },
            )
        ) {
            val profileId = it.arguments?.getLong(Screen.KEY_PROFILE_ID) ?: 0
            val profileType = it.arguments?.getString(Screen.KEY_PROFILE_TYPE).orEmpty()
            profileScreenContent(
                params = ProfileParams(
                    id = profileId,
                    type = ProfileType.valueOf(profileType)
                )
            )
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
