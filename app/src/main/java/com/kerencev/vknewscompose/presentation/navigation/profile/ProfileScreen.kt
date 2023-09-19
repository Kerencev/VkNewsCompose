package com.kerencev.vknewscompose.presentation.navigation.profile

import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.encode
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.navigation.main.Screen
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams

sealed class ProfileScreen(
    override val route: String
) : Screen {

    object Graph : ProfileScreen(GRAPH_KEY)

    object Profile : ProfileScreen(ROUTE_PROFILE) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile"

        fun getRouteWithArgs(profileParams: ProfileParams): String {
            return "$ROUTE_FOR_ARGS/${profileParams.id}/${profileParams.type.name}"
        }
    }

    object ProfilePhotos : ProfileScreen(ROUTE_PROFILE_PHOTOS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_profile_photos"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object Friends : ProfileScreen(ROUTE_FRIENDS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_friends"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object Comments : ProfileScreen(ROUTE_COMMENTS) {
        const val ROUTE_FOR_ARGS = "${GRAPH_KEY}_comments"

        fun getRouteWithArgs(newsModel: NewsModelUi): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    companion object {
        const val GRAPH_KEY = "profile_screen"

        private const val ROUTE_PROFILE =
            "${Profile.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}/{${Screen.KEY_PROFILE_TYPE}}"
        private const val ROUTE_PROFILE_PHOTOS =
            "${ProfilePhotos.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
        private const val ROUTE_FRIENDS = "${Friends.ROUTE_FOR_ARGS}/{${Screen.KEY_PROFILE_ID}}"
        private const val ROUTE_COMMENTS =
            "${Comments.ROUTE_FOR_ARGS}/{${Screen.KEY_NEWS_POST}}"
    }
}
