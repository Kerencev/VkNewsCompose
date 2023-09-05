package com.kerencev.vknewscompose.presentation.navigation.search

import com.kerencev.vknewscompose.presentation.navigation.main.Screen

sealed class SearchScreen(
    override val route: String
) : Screen {

    object Graph : SearchScreen(ROUTE_SEARCH_GRAPH)

    object Search : SearchScreen(ROUTE_SEARCH)

    companion object {
        private const val ROUTE_SEARCH_GRAPH = "search_graph"
        private const val ROUTE_SEARCH = "search"
    }
}
