package com.kerencev.vknewscompose.presentation.screens.home

import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    object Loading : HomeScreenState()

    data class Home(
        val news: List<NewsModel>,
        val nextDataIsLoading: Boolean = false
    ) : HomeScreenState()

}
