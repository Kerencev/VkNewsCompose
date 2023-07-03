package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    /**
     * Get news items list
     * @param isRefresh - loading the first page or the next
     */
    fun getNewsFeed(isRefresh: Boolean): Flow<List<NewsModel>>

    /**
     * @return updated model with an increased or decreased number of likes, depending on the previous status
     */
    fun changeLikeStatus(newsModel: NewsModel): Flow<NewsModel>

    /**
     * Just for fun :-)
     */
    fun deleteNews(newsModel: NewsModel): Flow<Unit>

}