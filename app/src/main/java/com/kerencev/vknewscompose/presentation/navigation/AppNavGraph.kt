package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerParams

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    photosPagerScreenContent: @Composable (params: PhotosPagerParams) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            mainScreenContent()
        }
        composable(
            route = Screen.PhotosPager.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PHOTO_TYPE) {
                    type = NavType.StringType
                },
                navArgument(name = Screen.KEY_SELECTED_PHOTO) {
                    type = NavType.IntType
                },
                navArgument(name = Screen.KEY_NEWS_MODEL_ID) {
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_USER_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val type = it.arguments?.getString(Screen.KEY_PHOTO_TYPE).orEmpty()
            val selectedPhotoNumber = it.arguments?.getInt(Screen.KEY_SELECTED_PHOTO) ?: 0
            val newsModelId = it.arguments?.getLong(Screen.KEY_NEWS_MODEL_ID) ?: 0
            val userId = it.arguments?.getLong(Screen.KEY_USER_ID) ?: 0
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