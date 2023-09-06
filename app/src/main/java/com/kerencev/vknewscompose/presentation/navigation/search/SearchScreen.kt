package com.kerencev.vknewscompose.presentation.navigation.search

import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

sealed class SearchScreen(
    override val route: String
) : Screen {

    object Graph : SearchScreen(GRAPH_KEY)

    object Search : SearchScreen(ROUTE_SEARCH)

    object Profile : SearchScreen(ROUTE_PROFILE) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile"

        fun getRouteWithArgs(profileParams: ProfileParams): String {
            return "$ROUTE_FOR_ARGS/${profileParams.id}/${profileParams.type.name}"
        }
    }

    object ProfilePhotos : SearchScreen(ROUTE_PROFILE_PHOTOS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile_photos"

        fun getRouteWithArgs(profileId: Long): String {
            return "$ROUTE_FOR_ARGS/$profileId"
        }
    }

    object Friends : SearchScreen(ROUTE_FRIENDS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_friends"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    companion object {
        private const val GRAPH_KEY = "search_graph"
        private const val ROUTE_SEARCH = "${GRAPH_KEY}_search"
        private const val ROUTE_PROFILE =
            "${Profile.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}/{${Screen.KEY_PROFILE_TYPE}}"
        private const val ROUTE_PROFILE_PHOTOS =
            "${ProfilePhotos.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
        private const val ROUTE_FRIENDS = "${Friends.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
    }
}
