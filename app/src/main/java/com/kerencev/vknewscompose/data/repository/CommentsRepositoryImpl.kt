package com.kerencev.vknewscompose.data.repository

import android.app.Application
import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.api.ApiFactory
import com.kerencev.vknewscompose.data.mapper.CommentsMapper
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry

class CommentsRepositoryImpl(
    application: Application
) : CommentsRepository {

    companion object {
        private const val RETRY_COUNT = 2L
        private const val RETRY_DELAY = 3_000L
    }

    private val apiService = ApiFactory.apiService
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)
    private val commentsMapper = CommentsMapper()

    override fun getComments(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>> =
        flow {
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