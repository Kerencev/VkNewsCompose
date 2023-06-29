package com.kerencev.vknewscompose.domain.use_cases.delete_news

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface DeleteNewsUseCase {

    operator fun invoke(newsModel: NewsModel): Flow<DataResult<Unit>>
}