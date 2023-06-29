package com.kerencev.vknewscompose.domain.use_cases.get_news

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface GetNewsUseCase {

    operator fun invoke(): Flow<DataResult<List<NewsModel>>>
}