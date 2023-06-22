package com.kerencev.vknewscompose.presentation.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.data.repository.NewsFeedRepository
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import com.kerencev.vknewscompose.presentation.common.ScreenState
import com.kerencev.vknewscompose.presentation.utils.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val newsFlow = repository.news

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
            repository.loadNextData()
        }
    }

    fun changeLikesStatus(newsModel: NewsModel) {
        viewModelScope.launch {
            repository.changeLikeStatus(newsModel)
        }
    }

    fun onNewsItemDismiss(newsModel: NewsModel) {
        viewModelScope.launch {
            repository.deleteNews(newsModel)
        }
    }

}

data class Home(
    val news: List<NewsModel>,
    val nextDataIsLoading: Boolean = false
)