package com.kerencev.vknewscompose.presentation.navigation.main

import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerParams

sealed class MainScreen(
    override val route: String
) : Screen {

    object Main : MainScreen(ROUTE_MAIN)

    object PhotosPager : MainScreen(ROUTE_PHOTOS_PAGER) {
        private const val ROUTE_FOR_ARGS = "photos_pager"

        fun getRouteWithArgs(params: PhotosPagerParams): String {
            return "$ROUTE_FOR_ARGS/${params.selectedPhotoNumber}" +
                    "/${params.photoType.name}/${params.newsModelId}/${params.userId}"
        }
    }

    companion object {
        const val KEY_SELECTED_PHOTO = "selected_photo"
        const val KEY_PHOTO_TYPE = "photo_type"
        const val KEY_NEWS_MODEL_ID = "news_model_id"
        const val KEY_USER_ID = "user_id"

        private const val ROUTE_MAIN = "main"
        private const val ROUTE_PHOTOS_PAGER =
            "photos_pager/{$KEY_SELECTED_PHOTO}/{$KEY_PHOTO_TYPE}/{$KEY_NEWS_MODEL_ID}/{$KEY_USER_ID}"
    }
}
