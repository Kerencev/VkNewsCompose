package com.kerencev.vknewscompose.presentation.navigation.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.navigation.main.getNewsPost
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileId
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

private const val PROFILE_ID_ERROR = "profileScreenNavGraph: profile id is null"
private const val PROFILE_TYPE_ERROR = "profileScreenNavGraph: profile type is null"
private const val NEWS_POST_ERROR = "profileScreenNavGraph: NewsPost is null"

fun NavGraphBuilder.profileScreenNavGraph(
    profileScreenContent: @Composable (params: ProfileParams) -> Unit,
    profilePhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
) {
    navigation(
        startDestination = ProfileScreen.Profile.route,
        route = ProfileScreen.Graph.route
    ) {
        composable(
            route = ProfileScreen.Profile.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT, Screen.PROFILE_TYPE_ARGUMENT)
        ) {
            profileScreenContent(
                params = ProfileParams(
                    id = it.getProfileId() ?: error(PROFILE_ID_ERROR),
                    type = ProfileType.valueOf(it.getProfileType() ?: PROFILE_TYPE_ERROR)
                )
            )
        }

        composable(
            route = ProfileScreen.ProfilePhotos.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) {
            profilePhotosScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR))
        }

        composable(
            route = ProfileScreen.Friends.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) {
            friendsScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR))
        }

        composable(
            route = ProfileScreen.Comments.route,
            arguments = listOf(Screen.NEWS_POST_ARGUMENT)
        ) {
            commentsScreenContent(it.getNewsPost() ?: error(NEWS_POST_ERROR))
        }
    }
}
