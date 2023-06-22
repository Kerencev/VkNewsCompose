package com.kerencev.vknewscompose.presentation.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.data.repository.NewsFeedRepositoryImpl
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.use_cases.ChangeLikeStatusUseCase
import com.kerencev.vknewscompose.domain.use_cases.DeleteNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.GetNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.LoadNextNewsUseCase
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.utils.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getNewsUseCase = GetNewsUseCase(repository)
    private val loadNexNewsUseCase = LoadNextNewsUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deleteNewsUseCase = DeleteNewsUseCase(repository)

    private val newsFlow = getNewsUseCase()

    private val loadNexDataFlow = MutableSharedFlow<ScreenState<Home>>()

    val screenState = newsFlow
        .filter { it.isNotEmpty() }
        .map { ScreenState.Content(Home(it)) as ScreenState<Home> }
        .onStart { emit(ScreenState.Loading) }
        .mergeWith(loadNexDataFlow)

    fun loadNextNews() {
        viewModelScope.launch {
            loadNexDataFlow.emit(
                ScreenState.Content(
                    data = Home(
                        news = newsFlow.value,
                        nextDataIsLoading = true
                    )
                )
            )
            loadNexNewsUseCase()
        }
    }

    fun changeLikesStatus(newsModel: NewsModel) {
        viewModelScope.launch {
            changeLikeStatusUseCase(newsModel)
        }
    }

    fun onNewsItemDismiss(newsModel: NewsModel) {
        viewModelScope.launch {
            deleteNewsUseCase(newsModel)
        }
    }

}

data class Home(
    val news: List<NewsModel>,
    val nextDataIsLoading: Boolean = false
)