package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsFeedRepository {

    private var startFromByDate: String? = null
    private val newsCacheByDate = mutableListOf<NewsModel>()

    private var startFromRecommended: String? = null
    private val newsCacheRecommended = mutableListOf<NewsModel>()

    override fun getNewsFeed(isRefresh: Boolean) = flow {
        if (isRefresh) {
            startFromByDate = null
            newsCacheByDate.clear()
        }
        val response = if (startFromByDate == null) apiService.loadNewsFeed()
        else apiService.loadNewsFeed(startFrom = startFromByDate.orEmpty())
        startFromByDate = response.response?.nextFrom
        val news = response.mapToModel()
        newsCacheByDate.addAll(news)
        emit(newsCacheByDate)
    }

    override fun getRecommended(isRefresh: Boolean) = flow {
        if (isRefresh) {
            startFromRecommended = null
            newsCacheRecommended.clear()
        }
        val response =
            if (startFromRecommended == null) apiService.loadRecommended()
            else apiService.loadRecommended(startFrom = startFromRecommended.orEmpty())
        startFromRecommended = response.response?.nextFrom
        val news = response.mapToModel()
        newsCacheRecommended.addAll(news)
        emit(newsCacheRecommended)
    }

    override fun changeLikeStatus(newsModel: NewsModel) = flow {
        if (newsModel.isLiked) {
            apiService.deleteLike(ownerId = newsModel.ownerId, postId = newsModel.id)
            emit(newsModel.copy(likesCount = newsModel.likesCount - 1, isLiked = false))
        } else {
            apiService.addLike(ownerId = newsModel.ownerId, postId = newsModel.id)
            emit(newsModel.copy(likesCount = newsModel.likesCount + 1, isLiked = true))
        }
    }

    override fun getPostPhotosById(newsModelId: Long): Flow<List<PhotoModel>> = flow {
        val post = newsCacheByDate.firstOrNull { it.id == newsModelId }
            ?: newsCacheRecommended.firstOrNull { it.id == newsModelId }
        if (post == null) emit(emptyList())
        else {
            emit(
                post.imageContent.map { image ->
                    PhotoModel(
                        id = image.id,
                        date = null,
                        lat = null,
                        long = null,
                        url = image.url,
                        height = image.height,
                        width = image.width,
                        text = "",
                        likes = 0,
                        reposts = 0
                    )
                }
            )
        }
    }

}