package com.kerencev.vknewscompose.data.repository

import android.app.Application
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.api.ApiFactory
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.data.mapper.CommentsMapper
import com.kerencev.vknewscompose.data.mapper.NewsFeedMapper
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.utils.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl(application: Application) : NewsFeedRepository {

    companion object {
        private const val RETRY_COUNT = 2L
        private const val RETRY_DELAY = 3_000L
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val newsMapper = NewsFeedMapper()
    private val commentsMapper = CommentsMapper()

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
            _newsModels.addAll(newsMapper.mapDtoToEntity(response))
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

    override suspend fun changeLikeStatus(newsModel: NewsModel) {
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

    override suspend fun deleteNews(newsModel: NewsModel) {
        _newsModels.remove(newsModel)
        refreshedListFlow.emit(newsModels)
    }

    fun getComments(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>> = flow {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = newsModel.communityId,
            postId = newsModel.id
        )
        emit(commentsMapper.mapResponseToComments(response))
    }
        .map { DataResult.Success(it) as DataResult<List<CommentModel>> }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

}