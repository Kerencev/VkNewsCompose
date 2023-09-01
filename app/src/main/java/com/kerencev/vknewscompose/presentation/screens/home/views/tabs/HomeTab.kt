package com.kerencev.vknewscompose.presentation.screens.home.views.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kerencev.vknewscompose.R

enum class HomeTab(val index: Int) {
    NEWS(0), RECOMMENDATION(1), SUGGESTED(2)
}

@Composable
fun HomeTab.toTitle(): String {
    return when (this) {
        HomeTab.NEWS -> stringResource(id = R.string.tab_title_news)
        HomeTab.RECOMMENDATION -> stringResource(id = R.string.tab_title_recommended)
        HomeTab.SUGGESTED -> stringResource(id = R.string.interesting)
    }
}