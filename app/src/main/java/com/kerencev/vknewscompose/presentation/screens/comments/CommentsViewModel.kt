package com.kerencev.vknewscompose.presentation.screens.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.repository.CommentsRepositoryImpl
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.use_cases.GetCommentsUseCase
import com.kerencev.vknewscompose.presentation.common.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    private val newsModel: NewsModel
) : AndroidViewModel(application) {

    private val repository = CommentsRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    private val _screenState = MutableStateFlow(ScreenState.Loading as ScreenState<List<CommentModel>>)
    val screenState = _screenState.asStateFlow()


    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            getCommentsUseCase(newsModel)
                .onStart { _screenState.emit(ScreenState.Loading) }
                .collect { result ->
                    when (result) {
                        is DataResult.Success -> _screenState.emit(
                            if (result.data.isEmpty()) ScreenState.Empty
                            else ScreenState.Content(data = result.data)
                        )

                        is DataResult.Error -> _screenState.emit(
                            ScreenState.Error(throwable = result.throwable)
                        )
                    }
                }
        }
    }

}