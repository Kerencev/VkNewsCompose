package com.kerencev.vknewscompose.presentation.navigation

import com.google.gson.Gson
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.utils.extensions.encode

sealed class Screen(
    val route: String
) {

    object Home : Screen(ROUTE_HOME)

    object News : Screen(ROUTE_NEWS)

    object Favourite : Screen(ROUTE_FAVOURITE)

    object Profile : Screen(ROUTE_PROFILE)

    object Comments : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(newsModel: NewsModel): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    companion object {
        const val KEY_NEWS_POST = "news_post"

        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS = "news"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_COMMENTS = "comments/{$KEY_NEWS_POST}"
    }
}
