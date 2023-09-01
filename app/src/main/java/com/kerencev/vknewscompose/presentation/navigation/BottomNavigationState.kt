package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.data.repository.AuthRepositoryImpl
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.model.ProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

class BottomNavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(getCorrectBottomTabRoute(route)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(newsModel: NewsModelUi) {
        navHostController.navigate(Screen.Comments.getRouteWithArgs(newsModel = newsModel))
    }

    fun navigateToProfile(profileParams: ProfileParams) {
        when (profileParams.type) {
            ProfileType.USER -> navHostController.navigate(
                Screen.UserProfileFromSuggested.getRouteWithArgs(
                    profileId = profileParams.id,
                    profileType = ProfileType.USER
                )
            )

            ProfileType.GROUP -> navigateToGroupProfile(profileParams.id)
        }
    }

    fun navigateToUserProfile(profileId: Long) {
        navHostController.navigate(
            Screen.UserProfile.getRouteWithArgs(
                profileId = profileId,
                profileType = ProfileType.USER
            )
        )
    }

    fun navigateToGroupProfile(profileId: Long) {
        navHostController.navigate(
            Screen.GroupProfile.getRouteWithArgs(
                profileId = profileId,
                profileType = ProfileType.GROUP
            )
        )
    }

    fun navigateToProfilePhotos(userId: Long) {
        navHostController.navigate(Screen.ProfilePhotos.getRouteWithArgs(userId = userId))
    }

    fun navigateToProfilePhotosFromSuggested(userId: Long) {
        navHostController.navigate(Screen.ProfilePhotosFromSuggested.getRouteWithArgs(userId = userId))
    }

    fun navigateToGroupPhotos(groupId: Long) {
        navHostController.navigate(Screen.GroupPhotos.getRouteWithArgs(groupId = groupId))
    }

    fun navigateToFriends(userId: Long) {
        navHostController.navigate(Screen.Friends.getRouteWithArgs(userId = userId))
    }

    fun navigateToFriendsFromSuggested(userId: Long) {
        navHostController.navigate(Screen.FriendsFromSuggested.getRouteWithArgs(userId = userId))
    }

    private fun getCorrectBottomTabRoute(route: String): String {
        return if (route == Screen.ProfileGraph.route)
            Screen.UserProfile.getRouteWithArgs(
                profileId = AuthRepositoryImpl.currentUserId,
                profileType = ProfileType.USER
            ) else route
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): BottomNavigationState {
    return remember {
        BottomNavigationState(navHostController = navHostController)
    }
}
