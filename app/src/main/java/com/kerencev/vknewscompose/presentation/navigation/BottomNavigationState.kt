package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

class BottomNavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
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

    fun navigateToProfilePhotos() {
        navHostController.navigate(Screen.ProfilePhotos.route)
    }

    fun navigateToFriends() {
        navHostController.navigate(Screen.Friends.route)
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
