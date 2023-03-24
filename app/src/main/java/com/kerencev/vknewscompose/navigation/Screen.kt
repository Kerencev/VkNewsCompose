package com.kerencev.vknewscompose.navigation

sealed class Screen(
    val route: String
) {
    object News : Screen(ROUTE_NEWS)
    object Favourite : Screen(ROUTE_FAVOURITE)
    object Profile : Screen(ROUTE_PROFILE)

    private companion object {
        const val ROUTE_NEWS = "news"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}
