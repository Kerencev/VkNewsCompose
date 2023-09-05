package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Screen {
    val route: String

    companion object {
        const val KEY_PROFILE_ID = "profile_id"
        const val KEY_PROFILE_TYPE = "profile_type"
        val PROFILE_ID_ARGUMENT = navArgument(name = KEY_PROFILE_ID) { type = NavType.LongType }
        val PROFILE_TYPE_ARGUMENT =
            navArgument(name = KEY_PROFILE_TYPE) { type = NavType.StringType }
    }
}

fun NavBackStackEntry.getProfileId(): Long? {
    return this.arguments?.getLong(Screen.KEY_PROFILE_ID)
}

fun NavBackStackEntry.getProfileType(): String? {
    return this.arguments?.getString(Screen.KEY_PROFILE_TYPE)
}