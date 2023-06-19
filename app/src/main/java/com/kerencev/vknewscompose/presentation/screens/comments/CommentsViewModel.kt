package com.kerencev.vknewscompose.presentation.screens.comments

import android.app.Application
import androidx.lifecycle.*
import com.kerencev.vknewscompose.data.repository.NewsFeedRepository
import com.kerencev.vknewscompose.domain.model.news_feed.CommentModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    newsModel: NewsModel
) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        loadComments(newsModel)
    }

    private fun loadComments(newsModel: NewsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = repository.getComments(newsModel)
            _screenState.postValue(
                CommentsScreenState.Comments(
                    newsModel = newsModel,
                    comments = comments
                )
            )
        }
    }

}