package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        emit(DataResult.Loading)
        val response = if (startFrom == null) apiService.loadNewsFeed(getAccessToken())
        else apiService.loadNewsFeed(getAccessToken(), startFrom.orEmpty())
        startFrom = response.response?.nextFrom
        emit(DataResult.Success(newsMapper.mapToEntity(response)))
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }
        .flowOn(Dispatchers.IO)

    override fun changeLikeStatus(newsModel: NewsModel): Flow<DataResult<NewsModel>> = flow {
        emit(DataResult.Loading)
        if (newsModel.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(
                DataResult.Success(
                    newsModel.copy(
                        likesCount = newsModel.likesCount - 1,
                        isLiked = false
                    )
                )
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(
                DataResult.Success(
                    newsModel.copy(
                        likesCount = newsModel.likesCount + 1,
                        isLiked = true
                    )
                )
            )
        }
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }
        .flowOn(Dispatchers.IO)

    override fun deleteNews(newsModel: NewsModel): Flow<DataResult<Unit>> = flow {
        emit(DataResult.Loading)
        delay(300)
        emit(DataResult.Success(Unit))
    }
        .flowOn(Dispatchers.IO)

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: error("Token is null")
    }

}