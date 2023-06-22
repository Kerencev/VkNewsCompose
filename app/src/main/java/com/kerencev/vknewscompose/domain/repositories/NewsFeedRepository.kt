package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getNewsFeed(): StateFlow<List<NewsModel>>

    suspend fun loadNextNews()

    suspend fun deleteNews(newsModel: NewsModel)

    suspend fun changeLikeStatus(newsModel: NewsModel)

}