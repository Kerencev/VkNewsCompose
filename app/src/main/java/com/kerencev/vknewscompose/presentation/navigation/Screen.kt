package com.kerencev.vknewscompose.presentation.navigation

import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.encode
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

sealed class Screen(
    val route: String
) {

    object Main : Screen(ROUTE_MAIN)

    object PhotosPager : Screen(ROUTE_PHOTOS_PAGER) {
        private const val ROUTE_FOR_ARGS = "photos_pager"

        fun getRouteWithArgs(initialNumber: Int): String {
            return "$ROUTE_FOR_ARGS/$initialNumber"
        }
    }

    object Home : Screen(ROUTE_HOME)

    object News : Screen(ROUTE_NEWS)

    object Favourite : Screen(ROUTE_FAVOURITE)

    object ProfileStart : Screen(ROUTE_PROFILE_START)

    object Profile : Screen(ROUTE_PROFILE)

    object ProfilePhotos : Screen(ROUTE_PROFILE_PHOTOS)

    object Comments : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(newsModel: NewsModelUi): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    companion object {
        const val KEY_NEWS_POST = "news_post"
        const val KEY_SELECTED_PHOTO = "selected_photo"

        private const val ROUTE_MAIN = "main"
        private const val ROUTE_PHOTOS_PAGER = "photos_pager/{$KEY_SELECTED_PHOTO}"
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS = "news"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_PROFILE_START = "profile_start"
        private const val ROUTE_PROFILE = "profile"
        private const val ROUTE_PROFILE_PHOTOS = "profile_photos"
        private const val ROUTE_COMMENTS = "comments/{$KEY_NEWS_POST}"
    }
}
