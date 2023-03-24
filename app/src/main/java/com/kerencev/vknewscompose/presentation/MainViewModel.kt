package com.kerencev.vknewscompose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<NewsModel>().apply {
        var currentTime = System.currentTimeMillis()
        repeat(100) { index ->
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

    private val _newsData = MutableLiveData<List<NewsModel>>(initialList)
    val newsData: LiveData<List<NewsModel>>
        get() = _newsData

    fun onStatisticClick(newsId: Int, type: NewsStatisticType) {
        val newsList = _newsData.value?.toMutableList() ?: mutableListOf()
        val oldNewsModel = newsList.find { it.id == newsId }
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
            _newsData.value = newsList
        }
    }

    fun onNewsItemDismiss(newsModel: NewsModel) {
        val newsList = _newsData.value?.toMutableList() ?: mutableListOf()
        newsList.remove(newsModel)
        _newsData.value = newsList
    }

}