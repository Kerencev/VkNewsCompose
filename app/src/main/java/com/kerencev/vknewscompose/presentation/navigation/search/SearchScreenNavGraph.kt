package com.kerencev.vknewscompose.presentation.navigation.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileId
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

private const val PROFILE_ID_ERROR = "searchScreenNavGraph: profile id is null"
private const val PROFILE_TYPE_ERROR = "searchScreenNavGraph: profile type is null"

fun NavGraphBuilder.searchScreenNavGraph(
    searchScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable (params: ProfileParams) -> Unit,
    profilePhotosScreenContent: @Composable (groupId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    navigation(
        startDestination = SearchScreen.Search.route,
        route = SearchScreen.Graph.route
    ) {
        composable(SearchScreen.Search.route) {
            searchScreenContent()
        }
        composable(
            route = SearchScreen.Profile.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT, Screen.PROFILE_TYPE_ARGUMENT)
        ) {
            profileScreenContent(
                params = ProfileParams(
                    id = it.getProfileId() ?: error(PROFILE_ID_ERROR),
                    type = ProfileType.valueOf(it.getProfileType() ?: error(PROFILE_TYPE_ERROR))
                )
            )
        }
        composable(
            route = SearchScreen.ProfilePhotos.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) {
            profilePhotosScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR))
        }
        composable(
            route = SearchScreen.Friends.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) {
            friendsScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR))
        }
    }
}
