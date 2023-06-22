package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {

    fun getComments(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>>
}