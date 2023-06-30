package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
    private val newsMapper: NewsFeedMapper
) : NewsFeedRepository {

    companion object {
        private const val RETRY_COUNT = 2L
        private const val RETRY_DELAY = 3_000L
    }

    private val token
        get() = VKAccessToken.restore(storage)

    private var startFrom: String? = null

    override fun getNewsFeed() = flow {
        val response = if (startFrom == null) apiService.loadNewsFeed(getAccessToken())
        else apiService.loadNewsFeed(getAccessToken(), startFrom.orEmpty())
        startFrom = response.response?.nextFrom
        emit(newsMapper.mapToEntity(response))
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .flowOn(Dispatchers.IO)

    override fun changeLikeStatus(newsModel: NewsModel) = flow {
        if (newsModel.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(newsModel.copy(likesCount = newsModel.likesCount - 1, isLiked = false))
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(newsModel.copy(likesCount = newsModel.likesCount + 1, isLiked = true))
        }
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .flowOn(Dispatchers.IO)

    override fun deleteNews(newsModel: NewsModel): Flow<Unit> = flow {
        delay(300)
        emit(Unit)
    }
        .flowOn(Dispatchers.IO)

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: error("Token is null")
    }

}