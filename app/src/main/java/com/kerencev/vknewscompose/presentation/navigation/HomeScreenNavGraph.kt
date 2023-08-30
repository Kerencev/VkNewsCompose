package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.model.ProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    groupProfileScreenContent: @Composable (params: ProfileParams) -> Unit,
    groupPhotosScreenContent: @Composable (groupId: Long) -> Unit,
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
                params = ProfileParams(
                    id = profileId,
                    type = ProfileType.valueOf(profileType)
                )
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
    }
}
