package com.kerencev.vknewscompose.domain.use_cases.get_comments

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsUseCaseImpl @Inject constructor(
    private val repository: CommentsRepository
) : GetCommentsUseCase {

    override operator fun invoke(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>> {
        return repository.getComments(newsModel)
    }

}