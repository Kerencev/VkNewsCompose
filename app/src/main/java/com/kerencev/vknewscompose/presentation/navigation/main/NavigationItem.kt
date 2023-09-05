package com.kerencev.vknewscompose.presentation.navigation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kerencev.vknewscompose.presentation.navigation.home.HomeScreen
import com.kerencev.vknewscompose.presentation.navigation.profile.ProfileScreen
import com.kerencev.vknewscompose.presentation.navigation.search.SearchScreen

sealed class NavigationItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {

    object Home : NavigationItem(
        screen = HomeScreen.Graph,
        title = TITLE_HOME,
        icon = Icons.Outlined.Home
    )

    object Search : NavigationItem(
        screen = SearchScreen.Graph,
        title = TITLE_SEARCH,
        icon = Icons.Outlined.Search
    )

    object Profile : NavigationItem(
        screen = ProfileScreen.Graph,
        title = TITLE_PROFILE,
        icon = Icons.Outlined.Person
    )

    private companion object {
        const val TITLE_HOME = "Главная"
        const val TITLE_SEARCH = "Поиск"
        const val TITLE_PROFILE = "Профиль"
    }

}