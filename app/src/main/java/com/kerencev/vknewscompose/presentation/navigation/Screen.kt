package com.kerencev.vknewscompose.presentation.navigation

import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.encode
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.model.PhotoType

sealed class Screen(
    val route: String
) {

    object Main : Screen(ROUTE_MAIN)

    object PhotosPager : Screen(ROUTE_PHOTOS_PAGER) {
        private const val ROUTE_FOR_ARGS = "photos_pager"

        fun getRouteWithArgs(
            userId: Long,
            type: PhotoType,
            initialNumber: Int,
            newsModelId: Long
        ): String {
            return "$ROUTE_FOR_ARGS/$initialNumber/${type.name}/$newsModelId/$userId"
        }
    }

    object HomeGraph : Screen(ROUTE_HOME)

    object News : Screen(ROUTE_NEWS)

    object RecommendationGraph : Screen(ROUTE_RECOMMENDATION_GRAPH)

    object Recommendation : Screen(ROUTE_RECOMMENDATION)

    object ProfileGraph : Screen(ROUTE_PROFILE_GRAPH)

    object Profile : Screen(ROUTE_PROFILE) {
        private const val ROUTE_FOR_ARGS = "profile"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object ProfilePhotos : Screen(ROUTE_PROFILE_PHOTOS) {
        private const val ROUTE_FOR_ARGS = "profile_photos"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object CommentsNews : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(newsModel: NewsModelUi): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    object CommentsRecommendation : Screen(ROUTE_COMMENTS_RECOMMENDATION) {
        private const val ROUTE_FOR_ARGS = "comments_recommendation"

        fun getRouteWithArgs(newsModel: NewsModelUi): String {
            val newsPostJson = Gson().toJson(newsModel)
            return "$ROUTE_FOR_ARGS/${newsPostJson.encode()}"
        }

    }

    object Friends : Screen(ROUTE_FRIENDS) {
        private const val ROUTE_FOR_ARGS = "friends"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    companion object {
        const val KEY_NEWS_POST = "news_post"
        const val KEY_SELECTED_PHOTO = "selected_photo"
        const val KEY_PHOTO_TYPE = "photo_type"
        const val KEY_NEWS_MODEL_ID = "news_model_id"
        const val KEY_USER_ID = "user_id"
        const val KEY_FRIENDS = "key_friends"

        private const val ROUTE_MAIN = "main"
        private const val ROUTE_PHOTOS_PAGER =
            "photos_pager/{$KEY_SELECTED_PHOTO}/{$KEY_PHOTO_TYPE}/{$KEY_NEWS_MODEL_ID}/{$KEY_USER_ID}"
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS = "news"
        private const val ROUTE_RECOMMENDATION_GRAPH = "recommendation_graph"
        private const val ROUTE_RECOMMENDATION = "recommendation"
        private const val ROUTE_PROFILE_GRAPH = "profile_graph"
        private const val ROUTE_PROFILE = "profile/{$KEY_USER_ID}"
        private const val ROUTE_PROFILE_PHOTOS = "profile_photos/{$KEY_USER_ID}"
        private const val ROUTE_COMMENTS = "comments/{$KEY_NEWS_POST}"
        private const val ROUTE_COMMENTS_RECOMMENDATION = "comments_recommendation/{$KEY_NEWS_POST}"
        private const val ROUTE_FRIENDS = "friends/{$KEY_FRIENDS}"
    }
}
