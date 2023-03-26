package com.kerencev.vknewscompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.domain.model.NewsModel
import com.kerencev.vknewscompose.extensions.getParcelableNew

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModel) -> Unit,
) {
    navigation(
        startDestination = Screen.News.route,
        route = Screen.Home.route
    ) {
        composable(Screen.News.route) {
            newsScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_NEWS_POST) {
                    type = NewsModel.NavigationType
                }
            )
        ) {
            val newsPost =
                it.arguments?.getParcelableNew(Screen.KEY_NEWS_POST, NewsModel::class.java)
                    ?: throw RuntimeException("Args for comments screen is null")
            commentsScreenContent(newsPost)
        }

    }
}
