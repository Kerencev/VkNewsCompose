package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerParams

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    photosPagerScreenContent: @Composable (params: PhotosPagerParams) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = MainScreen.Main.route
    ) {
        composable(MainScreen.Main.route) {
            mainScreenContent()
        }
        composable(
            route = MainScreen.PhotosPager.route,
            arguments = listOf(
                navArgument(name = MainScreen.KEY_PHOTO_TYPE) { type = NavType.StringType },
                navArgument(name = MainScreen.KEY_SELECTED_PHOTO) { type = NavType.IntType },
                navArgument(name = MainScreen.KEY_NEWS_MODEL_ID) { type = NavType.LongType },
                navArgument(name = MainScreen.KEY_USER_ID) { type = NavType.LongType }
            )
        ) {
            val type = it.arguments?.getString(MainScreen.KEY_PHOTO_TYPE).orEmpty()
            val selectedPhotoNumber = it.arguments?.getInt(MainScreen.KEY_SELECTED_PHOTO) ?: 0
            val newsModelId = it.arguments?.getLong(MainScreen.KEY_NEWS_MODEL_ID) ?: 0
            val userId = it.arguments?.getLong(MainScreen.KEY_USER_ID) ?: 0
            photosPagerScreenContent(
                params = PhotosPagerParams(
                    userId = userId,
                    photoType = PhotoType.valueOf(type),
                    selectedPhotoNumber = selectedPhotoNumber,
                    newsModelId = newsModelId
                )
            )
        }
    }
}