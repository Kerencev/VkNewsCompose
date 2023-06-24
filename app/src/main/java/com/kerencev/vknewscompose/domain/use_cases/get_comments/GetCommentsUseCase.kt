package com.kerencev.vknewscompose.domain.use_cases.get_comments

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface GetCommentsUseCase {

    operator fun invoke(newsModel: NewsModel): Flow<DataResult<List<CommentModel>>>
}