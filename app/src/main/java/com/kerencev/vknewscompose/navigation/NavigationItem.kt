package com.kerencev.vknewscompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {

    object Home : NavigationItem(
        screen = Screen.Home,
        title = TITLE_HOME,
        icon = Icons.Outlined.Home
    )

    object Favourite : NavigationItem(
        screen = Screen.Favourite,
        title = TITLE_FAVOURITES,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        title = TITLE_PROFILE,
        icon = Icons.Outlined.Person
    )

    private companion object {
        const val TITLE_HOME = "Главная"
        const val TITLE_FAVOURITES = "Избранное"
        const val TITLE_PROFILE = "Профиль"
    }

}