package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.recommendationScreenNavGraph(
    recommendationScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.Recommendation.route,
        route = Screen.RecommendationGraph.route
    ) {
        composable(Screen.Recommendation.route) {
            recommendationScreenContent()
        }
    }
}