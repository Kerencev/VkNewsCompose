package com.kerencev.vknewscompose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {

    object Home : NavigationItem(
        screen = Screen.HomeGraph,
        title = TITLE_HOME,
        icon = Icons.Outlined.Home
    )

    object Recommendation : NavigationItem(
        screen = Screen.RecommendationGraph,
        title = TITLE_RECOMMENDATION,
        icon = Icons.Default.List
    )

    object Profile : NavigationItem(
        screen = Screen.ProfileGraph,
        title = TITLE_PROFILE,
        icon = Icons.Outlined.Person
    )

    private companion object {
        const val TITLE_HOME = "Главная"
        const val TITLE_RECOMMENDATION = "Рекоммендации"
        const val TITLE_PROFILE = "Профиль"
    }

}