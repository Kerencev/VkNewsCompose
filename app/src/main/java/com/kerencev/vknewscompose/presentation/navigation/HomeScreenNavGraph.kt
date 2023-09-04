package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    groupProfileScreenContent: @Composable (params: ProfileParams) -> Unit,
    groupPhotosScreenContent: @Composable (groupId: Long) -> Unit,
    userProfileScreenContent: @Composable (params: ProfileParams) -> Unit,
    userPhotosScreenContent: @Composable (userId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    navigation(
        startDestination = Screen.News.route,
        route = Screen.HomeGraph.route
    ) {
        composable(Screen.News.route) {
            newsScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_NEWS_POST) { type = NewsModelUi.NavigationType }
            )
        ) {
            val newsPost =
                it.arguments?.getParcelableNew(Screen.KEY_NEWS_POST, NewsModelUi::class.java)
                    ?: throw RuntimeException("Args for comments screen is null")
            commentsScreenContent(newsPost)
        }
        composable(
            route = Screen.GroupProfile.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PROFILE_ID) { type = NavType.LongType },
                navArgument(name = Screen.KEY_PROFILE_TYPE) { type = NavType.StringType }
            )
        ) {
            val profileId = it.arguments?.getLong(Screen.KEY_PROFILE_ID) ?: 0
            val profileType = it.arguments?.getString(Screen.KEY_PROFILE_TYPE).orEmpty()
            groupProfileScreenContent(
                params = ProfileParams(id = profileId, type = ProfileType.valueOf(profileType))
            )
        }
        composable(
            route = Screen.GroupPhotos.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PROFILE_ID) { type = NavType.LongType }
            )
        ) {
            val groupId = it.arguments?.getLong(Screen.KEY_PROFILE_ID) ?: 0
            groupPhotosScreenContent(groupId)
        }
        composable(
            route = Screen.UserProfileFromSuggested.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PROFILE_ID) { type = NavType.LongType },
                navArgument(name = Screen.KEY_PROFILE_TYPE) { type = NavType.StringType }
            )
        ) {
            val profileId = it.arguments?.getLong(Screen.KEY_PROFILE_ID) ?: 0
            val profileType = it.arguments?.getString(Screen.KEY_PROFILE_TYPE).orEmpty()
            userProfileScreenContent(
                params = ProfileParams(id = profileId, type = ProfileType.valueOf(profileType))
            )
        }
        composable(
            route = Screen.ProfilePhotosFromSuggested.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_USER_ID) { type = NavType.LongType }
            )
        ) {
            val groupId = it.arguments?.getLong(Screen.KEY_USER_ID) ?: 0
            userPhotosScreenContent(groupId)
        }
        composable(
            route = Screen.FriendsFromSuggested.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_FRIENDS) { type = NavType.LongType }
            )
        ) {
            val groupId = it.arguments?.getLong(Screen.KEY_FRIENDS) ?: 0
            friendsScreenContent(groupId)
        }
    }
}
