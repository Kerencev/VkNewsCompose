package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    fun getNewsFeed(): Flow<List<NewsModel>>

    fun changeLikeStatus(newsModel: NewsModel): Flow<NewsModel>

    fun deleteNews(newsModel: NewsModel): Flow<Unit>

}