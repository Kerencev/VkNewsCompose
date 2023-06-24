package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.comments.CommentsMapper
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
    private val commentsMapper: CommentsMapper
) : CommentsRepository {

    companion object {
        private const val RETRY_COUNT = 2L
        private const val RETRY_DELAY = 3_000L
    }

    private val token
        get() = VKAccessToken.restore(storage)

    override fun getComments(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>> =
        flow {
            val response = apiService.getComments(
                token = getAccessToken(),
                ownerId = newsModel.communityId,
                postId = newsModel.id
            )
            emit(commentsMapper.mapToEntity(response))
        }
            .map { DataResult.Success(it) as DataResult<List<CommentModel>> }
            .retry(RETRY_COUNT) {
                delay(RETRY_DELAY)
                true
            }
            .catch { emit(DataResult.Error(it)) }
            .flowOn(Dispatchers.IO)

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }
}