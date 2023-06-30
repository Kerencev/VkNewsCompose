package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    fun getNewsFeed(): Flow<DataResult<List<NewsModel>>>

    fun deleteNews(newsModel: NewsModel): Flow<DataResult<Unit>>

    fun changeLikeStatus(newsModel: NewsModel): Flow<DataResult<NewsModel>>

}