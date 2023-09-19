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

    fun navigateToComments(from: NavigationItem, newsModel: NewsModelUi) {
        navHostController.navigate(
            when (from) {
                NavigationItem.Home -> HomeScreen.Comments.getRouteWithArgs(newsModel)
                NavigationItem.Profile -> ProfileScreen.Comments.getRouteWithArgs(newsModel)
                NavigationItem.Search -> SearchScreen.Comments.getRouteWithArgs(newsModel)
            }
        )
    }

    fun navigateToProfile(from: NavigationItem, params: ProfileParams) {
        navHostController.navigate(
            when (from) {
                NavigationItem.Home -> HomeScreen.Profile.getRouteWithArgs(params)
                NavigationItem.Profile -> ProfileScreen.Profile.getRouteWithArgs(params)
                NavigationItem.Search -> SearchScreen.Profile.getRouteWithArgs(params)
            }
        )
    }

    fun navigateToProfilePhotos(from: NavigationItem, profileId: Long) {
        navHostController.navigate(
            when (from) {
                NavigationItem.Home -> HomeScreen.ProfilePhotos.getRouteWithArgs(profileId)
                NavigationItem.Profile -> ProfileScreen.ProfilePhotos.getRouteWithArgs(profileId)
                NavigationItem.Search -> SearchScreen.ProfilePhotos.getRouteWithArgs(profileId)
            }
        )
    }

    fun navigateToFriends(from: NavigationItem, userId: Long) {
        navHostController.navigate(
            when (from) {
                NavigationItem.Home -> HomeScreen.Friends.getRouteWithArgs(userId)
                NavigationItem.Profile -> ProfileScreen.Friends.getRouteWithArgs(userId)
                NavigationItem.Search -> SearchScreen.Friends.getRouteWithArgs(userId)
            }
        )
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
