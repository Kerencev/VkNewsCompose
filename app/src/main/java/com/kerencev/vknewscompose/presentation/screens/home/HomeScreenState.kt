package com.kerencev.vknewscompose.presentation.screens.home

import com.kerencev.vknewscompose.domain.model.CommentModel
import com.kerencev.vknewscompose.domain.model.NewsModel

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    data class News(val news: List<NewsModel>) : HomeScreenState()

    data class Comments(val newsModel: NewsModel, val comments: List<CommentModel>) : HomeScreenState()

}
