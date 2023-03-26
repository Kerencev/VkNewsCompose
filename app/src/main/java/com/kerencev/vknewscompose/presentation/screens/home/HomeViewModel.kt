package com.kerencev.vknewscompose.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.domain.model.NewsModel
import com.kerencev.vknewscompose.presentation.screens.news.NewsStatisticType
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val initialNews = mutableListOf<NewsModel>().apply {
        var currentTime = System.currentTimeMillis()
        repeat(10) { index ->
            val postTime = currentTime - 300_000
            currentTime = postTime
            add(
                NewsModel(
                    id = index,
                    name = "Группа № $index",
                    postTime = postTime,
                    text = "Текст поста группы № $index",
                    viewsCount = Random.nextInt(5_000),
                    sharesCount = Random.nextInt(3_000),
                    commentsCount = Random.nextInt(500),
                    likesCount = Random.nextInt(4_000)
                )
            )
        }
    }
    private val initialState = HomeScreenState.Home(news = initialNews)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState>
        get() = _screenState

    fun onStatisticClick(newsModel: NewsModel, type: NewsStatisticType) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Home) return

        val newsList = currentState.news.toMutableList()
        val oldNewsModel = newsList.find { it.id == newsModel.id }
        oldNewsModel?.let { oldModel ->
            val newNewsModel = when (type) {
                NewsStatisticType.VIEWS -> {
                    oldModel.copy(viewsCount = oldModel.viewsCount + 1)
                }
                NewsStatisticType.SHARES -> {
                    oldModel.copy(sharesCount = oldModel.sharesCount + 1)
                }
                NewsStatisticType.COMMENTS -> {
                    oldModel.copy(commentsCount = oldModel.commentsCount + 1)
                }
                NewsStatisticType.LIKES -> {
                    oldModel.copy(likesCount = oldModel.likesCount + 1)
                }
            }
            newsList[newsList.indexOf(oldNewsModel)] = newNewsModel
            _screenState.value = HomeScreenState.Home(news = newsList)
        }
    }

    fun onNewsItemDismiss(newsModel: NewsModel) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Home) return

        val newsList = currentState.news.toMutableList()
        newsList.remove(newsModel)
        _screenState.value = HomeScreenState.Home(news = newsList)
    }

}