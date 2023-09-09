package com.kerencev.vknewscompose.presentation.navigation.home

import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.encode
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

sealed class HomeScreen(
    override val route: String
) : Screen {

    object Graph : HomeScreen(GRAPH_KEY)

    object News : HomeScreen(ROUTE_NEWS)

    object Comments : HomeScreen(ROUTE_COMMENTS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_comments"

        fun getRouteWithArgs(newsModel: NewsModelUi): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    object Profile : HomeScreen(ROUTE_PROFILE) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile"

        fun getRouteWithArgs(profileParams: ProfileParams): String {
            return "$ROUTE_FOR_ARGS/${profileParams.id}/${profileParams.type.name}"
        }
    }

    object ProfilePhotos : HomeScreen(ROUTE_PROFILE_PHOTOS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile_photos"

        fun getRouteWithArgs(profileId: Long): String {
            return "$ROUTE_FOR_ARGS/$profileId"
        }
    }

    object Friends : HomeScreen(ROUTE_FRIENDS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_friends"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    companion object {
        private const val GRAPH_KEY = "home_screen"

        private const val ROUTE_NEWS = "${GRAPH_KEY}_news"
        private const val ROUTE_COMMENTS = "${Comments.ROUTE_FOR_ARGS}/{${Screen.KEY_NEWS_POST}}"
        private const val ROUTE_PROFILE =
            "${Profile.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}/{${Screen.KEY_PROFILE_TYPE}}"
        private const val ROUTE_PROFILE_PHOTOS =
            "${ProfilePhotos.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
        private const val ROUTE_FRIENDS = "${Friends.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
    }
}
