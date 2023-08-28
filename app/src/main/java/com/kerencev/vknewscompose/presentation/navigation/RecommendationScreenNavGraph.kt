package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

fun NavGraphBuilder.recommendationScreenNavGraph(
    recommendationScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (NewsModelUi) -> Unit,
) {
    navigation(
        startDestination = Screen.Recommendation.route,
        route = Screen.RecommendationGraph.route
    ) {
        composable(Screen.Recommendation.route) {
            recommendationScreenContent()
        }
        composable(
            route = Screen.CommentsRecommendation.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_NEWS_POST) {
                    type = NewsModelUi.NavigationType
                }
            )
        ) {
            val newsPost =
                it.arguments?.getParcelableNew(Screen.KEY_NEWS_POST, NewsModelUi::class.java)
                    ?: throw RuntimeException("Args for comments screen is null")
            commentsScreenContent(newsPost)
        }
    }
}