package com.kerencev.vknewscompose.presentation.screens.home

import com.kerencev.vknewscompose.domain.model.NewsModel

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    data class Home(val news: List<NewsModel>) : HomeScreenState()

}
