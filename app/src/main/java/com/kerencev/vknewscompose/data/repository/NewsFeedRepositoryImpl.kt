package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.mergeWith
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
    private val newsMapper: NewsFeedMapper
) : NewsFeedRepository {

    companion object {
        private const val RETRY_DELAY = 3_000L
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val token
        get() = VKAccessToken.restore(storage)

    private val _newsModels = mutableListOf<NewsModel>()
    private val newsModels: List<NewsModel>
        get() = _newsModels.toList()

    private var nextFrom: String? = null

    private val nextDataEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<NewsModel>>()
    private val loadedListFlow = flow {
        nextDataEvents.emit(Unit)
        nextDataEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && newsModels.isNotEmpty()) {
                emit(newsModels)
                return@collect
            }
            val response = if (startFrom == null) apiService.loadNewsFeed(getAccessToken())
            else apiService.loadNewsFeed(getAccessToken(), startFrom)
            nextFrom = response.response?.nextFrom
            _newsModels.addAll(newsMapper.mapToEntity(response))
            emit(newsModels)
        }
    }
        .retry {
            delay(RETRY_DELAY)
            true
        }
    //TODO: Add through and error handling

    private val news: StateFlow<List<NewsModel>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = newsModels
        )

    override fun getNewsFeed() = news

    override suspend fun loadNextNews() {
        nextDataEvents.emit(Unit)
    }

    override suspend fun changeLikeStatus(newsModel: NewsModel) = withContext(Dispatchers.IO) {
        val response: LikesCountResponseDto = if (newsModel.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
        }
        val newLikesCount = response.likes?.count
        val newPost = newsModel.copy(likesCount = newLikesCount ?: 0, isLiked = !newsModel.isLiked)
        val postIndex = _newsModels.indexOf(newsModel)
        _newsModels[postIndex] = newPost
        refreshedListFlow.emit(newsModels)
    }

    override suspend fun deleteNews(newsModel: NewsModel) = withContext(Dispatchers.IO) {
        _newsModels.remove(newsModel)
        refreshedListFlow.emit(newsModels)
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

}