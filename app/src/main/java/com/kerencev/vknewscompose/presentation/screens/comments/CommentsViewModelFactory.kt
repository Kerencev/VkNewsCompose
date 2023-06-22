package com.kerencev.vknewscompose.presentation.screens.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kerencev.vknewscompose.domain.entities.NewsModel

class CommentsViewModelFactory(
    private val application: Application,
    private val newsModel: NewsModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(application = application, newsModel) as T
    }

}