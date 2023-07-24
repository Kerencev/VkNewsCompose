package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
) : CommentsRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    override fun getComments(newsModel: NewsModel) = flow {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = newsModel.communityId,
            postId = newsModel.id
        )
        emit(response.mapToModel())
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken() = token?.accessToken ?: error("Token is null")

}