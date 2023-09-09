package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kerencev.vknewscompose.presentation.extensions.getParcelableNew
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

interface Screen {
    val route: String

    companion object {
        const val KEY_PROFILE_ID = "profile_id"
        const val KEY_PROFILE_TYPE = "profile_type"
        const val KEY_NEWS_POST = "news_post"

        val PROFILE_ID_ARGUMENT = navArgument(name = KEY_PROFILE_ID) { type = NavType.LongType }
        val PROFILE_TYPE_ARGUMENT =
            navArgument(name = KEY_PROFILE_TYPE) { type = NavType.StringType }
        val NEWS_POST_ARGUMENT =
            navArgument(name = Screen.KEY_NEWS_POST) { type = NewsModelUi.NavigationType }
    }
}

fun NavBackStackEntry.getProfileId(): Long? {
    return this.arguments?.getLong(Screen.KEY_PROFILE_ID)
}

fun NavBackStackEntry.getProfileType(): String? {
    return this.arguments?.getString(Screen.KEY_PROFILE_TYPE)
}

fun NavBackStackEntry.getNewsPost(): NewsModelUi? {
    return this.arguments?.getParcelableNew(Screen.KEY_NEWS_POST, NewsModelUi::class.java)
}