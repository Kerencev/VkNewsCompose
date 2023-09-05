package com.kerencev.vknewscompose.presentation.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileId
import com.kerencev.vknewscompose.presentation.navigation.main.getProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

private const val NEWS_POST_ERROR =
    "NavGraphBuilder.homeScreenNavGraph: NewsPost for comments screen is null"
private const val PROFILE_ID_ERROR = "homeScreenNavGraph: profile id is null"
private const val PROFILE_TYPE_ERROR = "homeScreenNavGraph: profile type is null"

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
    profileScreenContent: @Composable (params: ProfileParams) -> Unit,
    groupPhotosScreenContent: @Composable (groupId: Long) -> Unit,
    friendsScreenContent: @Composable (userId: Long) -> Unit,
) {
    navigation(
        startDestination = HomeScreen.News.route,
        route = HomeScreen.Graph.route
    ) {
        composable(HomeScreen.News.route) {
            newsScreenContent()
        }
        composable(
            route = HomeScreen.Comments.route,
            arguments = listOf(
                navArgument(name = HomeScreen.KEY_NEWS_POST) { type = NewsModelUi.NavigationType }
            )
        ) {
            val newsPost =
                it.arguments?.getParcelableNew(HomeScreen.KEY_NEWS_POST, NewsModelUi::class.java)
                    ?: error(NEWS_POST_ERROR)
            commentsScreenContent(newsPost)
        }
        composable(
            route = HomeScreen.Profile.route,
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
            route = HomeScreen.ProfilePhotos.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) {
            groupPhotosScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR))
        }
        composable(
            route = HomeScreen.Friends.route,
            arguments = listOf(Screen.PROFILE_ID_ARGUMENT)
        ) { friendsScreenContent(it.getProfileId() ?: error(PROFILE_ID_ERROR)) }
    }
}
