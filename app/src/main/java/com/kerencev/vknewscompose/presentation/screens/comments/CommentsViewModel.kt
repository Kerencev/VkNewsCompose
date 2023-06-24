package com.kerencev.vknewscompose.presentation.screens.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.use_cases.get_comments.GetCommentsUseCase
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val newsModel: NewsModelUi,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val newsModelMapper: NewsModelMapper
) : ViewModel() {

    private val _screenState =
        MutableStateFlow(ScreenState.Loading as ScreenState<List<CommentModel>>)
    val screenState = _screenState.asStateFlow()


    init {
        loadData()
    }

    fun loadData() {
        getCommentsUseCase(newsModelMapper.mapToEntity(newsModel))
            .onStart { _screenState.emit(ScreenState.Loading) }
            .onEach { result ->
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
            .launchIn(viewModelScope)
    }

}