package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    photosSliderScreenContent: @Composable (Int) -> Unit
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
                navArgument(name = Screen.KEY_SELECTED_PHOTO) {
                    type = NavType.IntType
                }
            )
        ) {
            val selectedPhotoNumber = it.arguments?.getInt(Screen.KEY_SELECTED_PHOTO)
                ?: throw RuntimeException("Args for PhotosPager screen is null")
            photosSliderScreenContent(selectedPhotoNumber)
        }
    }
}