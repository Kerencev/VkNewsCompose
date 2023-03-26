package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kerencev.vknewscompose.domain.model.NewsModel

class CommentsViewModelFactory(
    private val newsModel: NewsModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(newsModel) as T
    }

}