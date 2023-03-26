package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.domain.model.CommentModel
import com.kerencev.vknewscompose.domain.model.NewsModel

class CommentsViewModel(
    newsModel: NewsModel
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(newsModel)
    }

    private fun loadComments(newsModel: NewsModel) {
        val comments = mutableListOf<CommentModel>().apply {
            repeat(20) {
                add(
                    CommentModel(
                        id = it,
                        authorName = "Сергей К.",
                        commentText = "Вот такой вот клмментарий",
                        commentDate = System.currentTimeMillis()
                    )
                )
            }
        }
        _screenState.value = CommentsScreenState.Comments(
            newsModel = newsModel,
            comments = comments
        )
    }

}