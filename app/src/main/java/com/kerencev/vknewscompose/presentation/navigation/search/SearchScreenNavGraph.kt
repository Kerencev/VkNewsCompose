package com.kerencev.vknewscompose.presentation.navigation.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.searchScreenNavGraph(
    searchScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = SearchScreen.Search.route,
        route = SearchScreen.Graph.route
    ) {
        composable(SearchScreen.Search.route) {
            searchScreenContent()
        }
    }
}