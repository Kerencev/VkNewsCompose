package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {

    /**
     * Get list of comments
     */
    fun getComments(newsModel: NewsModel): Flow<List<CommentModel>>
}