package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : CommentsRepository {

    override fun getComments(newsModel: NewsModel) = flow {
        val response = apiService.getComments(
            ownerId = newsModel.ownerId,
            postId = newsModel.id
        )
        emit(response.mapToModel())
    }

}