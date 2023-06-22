package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import kotlinx.coroutines.flow.Flow

class GetCommentsUseCase(
    private val repository: CommentsRepository
) {

    operator fun invoke(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>> {
        return repository.getComments(newsModel)
    }

}