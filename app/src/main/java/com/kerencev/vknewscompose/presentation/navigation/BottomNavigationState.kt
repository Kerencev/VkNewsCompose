package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileViewModel

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

    fun navigateToProfile(userId: Long) {
        navHostController.navigate(Screen.Profile.getRouteWithArgs(userId = userId))
    }

    fun navigateToProfilePhotos(userId: Long) {
        navHostController.navigate(Screen.ProfilePhotos.getRouteWithArgs(userId = userId))
    }

    fun navigateToFriends(userId: Long) {
        navHostController.navigate(Screen.Friends.getRouteWithArgs(userId = userId))
    }

    private fun getCorrectBottomTabRoute(route: String): String {
        return if (route == Screen.Profile.route)
            Screen.Profile.getRouteWithArgs(ProfileViewModel.DEFAULT_USER_ID) else route
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
