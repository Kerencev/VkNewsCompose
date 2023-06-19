package com.kerencev.vknewscompose.presentation.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.data.repository.NewsFeedRepository
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<HomeScreenState>(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState>
        get() = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = HomeScreenState.Loading
        loadNews()
    }

    fun changeLikesStatus(newsModel: NewsModel) {
        viewModelScope.launch {
            repository.changeLikeStatus(newsModel)
            _screenState.postValue(HomeScreenState.Home(news = repository.newsModels))
        }
    }

    fun loadNextNews() {
        _screenState.value = HomeScreenState.Home(
            news = repository.newsModels,
            nextDataIsLoading = true
        )
        loadNews()
    }

    fun onNewsItemDismiss(newsModel: NewsModel) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Home) return

        val newsList = currentState.news.toMutableList()
        newsList.remove(newsModel)
        _screenState.value = HomeScreenState.Home(news = newsList)
    }

    private fun loadNews() {
        viewModelScope.launch {
            val newsPosts = repository.loadNewsFeed()
            _screenState.postValue(HomeScreenState.Home(newsPosts))
        }
    }

}