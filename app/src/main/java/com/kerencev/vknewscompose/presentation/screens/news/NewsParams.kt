package com.kerencev.vknewscompose.presentation.screens.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kerencev.vknewscompose.R

data class NewsParams(
    val type: NewsType
)

enum class NewsType(val index: Int) {
    NEWS(index = 0),
    RECOMMENDATION(index = 1)
}

@Composable
fun NewsType.toTitle(): String {
    return when (this) {
        NewsType.NEWS -> stringResource(id = R.string.tab_title_news)
        NewsType.RECOMMENDATION -> stringResource(id = R.string.tab_title_recommended)
    }
}
