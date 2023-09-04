package com.kerencev.vknewscompose.presentation.navigation

import com.google.gson.Gson
import com.kerencev.vknewscompose.presentation.extensions.encode
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.model.ProfileType

//TODO: Если не получится избавиться от повторяющихся экранов, разбить на файлы по табам
sealed class Screen(
    val route: String
) {

    object Main : Screen(ROUTE_MAIN)

    object PhotosPager : Screen(ROUTE_PHOTOS_PAGER) {
        private const val ROUTE_FOR_ARGS = "photos_pager"

        fun getRouteWithArgs(
            userId: Long?,
            type: PhotoType,
            initialNumber: Int,
            newsModelId: Long?
        ): String {
            return "$ROUTE_FOR_ARGS/$initialNumber/${type.name}/${newsModelId ?: 0}/${userId ?: 0}"
        }
    }

    object HomeGraph : Screen(ROUTE_HOME)

    object News : Screen(ROUTE_NEWS)

    object RecommendationGraph : Screen(ROUTE_RECOMMENDATION_GRAPH)

    object Recommendation : Screen(ROUTE_RECOMMENDATION)

    object ProfileGraph : Screen(ROUTE_PROFILE_GRAPH)

    object UserProfile : Screen(ROUTE_USER_PROFILE) {
        private const val ROUTE_FOR_ARGS = "user_profile"

        fun getRouteWithArgs(profileId: Long, profileType: ProfileType): String {
            return "$ROUTE_FOR_ARGS/$profileId/${profileType.name}"
        }
    }

    object UserProfileFromSuggested : Screen(ROUTE_SUGGESTED_USER_PROFILE) {
        private const val ROUTE_FOR_ARGS = "suggested_user_profile"

        fun getRouteWithArgs(profileId: Long, profileType: ProfileType): String {
            return "$ROUTE_FOR_ARGS/$profileId/${profileType.name}"
        }
    }

    object GroupProfile : Screen(ROUTE_GROUP_PROFILE) {
        private const val ROUTE_FOR_ARGS = "group_profile"

        fun getRouteWithArgs(profileId: Long, profileType: ProfileType): String {
            return "${ROUTE_FOR_ARGS}/$profileId/${profileType.name}"
        }
    }

    object ProfilePhotos : Screen(ROUTE_PROFILE_PHOTOS) {
        private const val ROUTE_FOR_ARGS = "profile_photos"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object ProfilePhotosFromSuggested : Screen(ROUTE_SUGGESTED_PROFILE_PHOTOS) {
        private const val ROUTE_FOR_ARGS = "suggested_profile_photos"

        fun getRouteWithArgs(userId: Long): String {
            return "$ROUTE_FOR_ARGS/$userId"
        }
    }

    object GroupPhotos : Screen(ROUTE_GROUP_PHOTOS) {
        private const val ROUTE_FOR_ARGS = "group_photos"

        fun getRouteWithArgs(groupId: Long): String {
            return "$ROUTE_FOR_ARGS/$groupId"
        }
    }

    object Comments : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "comments"

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

    object FriendsFromSuggested : Screen(ROUTE_SUGGESTED_FRIEND) {
        private const val ROUTE_FOR_ARGS = "suggested_friends"

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
        const val KEY_PROFILE_ID = "profile_id"
        const val KEY_PROFILE_TYPE = "profile_type"
        const val KEY_FRIENDS = "key_friends"

        private const val ROUTE_MAIN = "main"
        private const val ROUTE_PHOTOS_PAGER =
            "photos_pager/{$KEY_SELECTED_PHOTO}/{$KEY_PHOTO_TYPE}/{$KEY_NEWS_MODEL_ID}/{$KEY_USER_ID}"
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS = "news"
        private const val ROUTE_RECOMMENDATION_GRAPH = "recommendation_graph"
        private const val ROUTE_RECOMMENDATION = "recommendation"
        private const val ROUTE_PROFILE_GRAPH = "profile_graph"
        private const val ROUTE_USER_PROFILE = "user_profile/{$KEY_PROFILE_ID}/{$KEY_PROFILE_TYPE}"
        private const val ROUTE_SUGGESTED_USER_PROFILE =
            "suggested_user_profile/{$KEY_PROFILE_ID}/{$KEY_PROFILE_TYPE}"
        private const val ROUTE_GROUP_PROFILE =
            "group_profile/{$KEY_PROFILE_ID}/{$KEY_PROFILE_TYPE}"
        private const val ROUTE_PROFILE_PHOTOS = "profile_photos/{$KEY_USER_ID}"
        private const val ROUTE_SUGGESTED_PROFILE_PHOTOS = "suggested_profile_photos/{$KEY_USER_ID}"
        private const val ROUTE_GROUP_PHOTOS = "group_photos/{$KEY_PROFILE_ID}"
        private const val ROUTE_COMMENTS = "comments/{$KEY_NEWS_POST}"
        private const val ROUTE_FRIENDS = "friends/{$KEY_FRIENDS}"
        private const val ROUTE_SUGGESTED_FRIEND = "suggested_friends/{$KEY_FRIENDS}"
    }
}
