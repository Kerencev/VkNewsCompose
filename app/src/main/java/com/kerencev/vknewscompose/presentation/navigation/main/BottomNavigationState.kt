package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.data.repository.AuthRepositoryImpl
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.home.HomeScreen
import com.kerencev.vknewscompose.presentation.navigation.profile.ProfileScreen
import com.kerencev.vknewscompose.presentation.navigation.search.SearchScreen
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
        navHostController.navigate(HomeScreen.Comments.getRouteWithArgs(newsModel))
    }

    fun navigateToProfile(from: NavigationItem, params: ProfileParams) {
        when (from) {
            NavigationItem.Home -> navHostController.navigate(
                HomeScreen.Profile.getRouteWithArgs(params)
            )

            NavigationItem.Profile -> navHostController.navigate(
                ProfileScreen.Profile.getRouteWithArgs(params)
            )

            NavigationItem.Search -> navHostController.navigate(
                SearchScreen.Profile.getRouteWithArgs(params)
            )
        }
    }

    fun navigateToProfilePhotos(from: NavigationItem, profileId: Long) {
        when (from) {
            NavigationItem.Home -> navHostController.navigate(
                HomeScreen.ProfilePhotos.getRouteWithArgs(profileId)
            )

            NavigationItem.Profile -> navHostController.navigate(
                ProfileScreen.ProfilePhotos.getRouteWithArgs(profileId)
            )

            NavigationItem.Search -> navHostController.navigate(
                SearchScreen.ProfilePhotos.getRouteWithArgs(profileId)
            )
        }
    }

    fun navigateToFriends(from: NavigationItem, userId: Long) {
        when (from) {
            NavigationItem.Home -> navHostController.navigate(
                HomeScreen.Friends.getRouteWithArgs(userId)
            )

            NavigationItem.Profile -> navHostController.navigate(
                ProfileScreen.Friends.getRouteWithArgs(userId)
            )

            NavigationItem.Search -> navHostController.navigate(
                SearchScreen.Friends.getRouteWithArgs(userId)
            )
        }
    }

    private fun getCorrectBottomTabRoute(route: String): String {
        return if (route == ProfileScreen.Graph.route)
            ProfileScreen.Profile.getRouteWithArgs(
                ProfileParams(
                    id = AuthRepositoryImpl.currentUserId,
                    type = ProfileType.USER
                )
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
