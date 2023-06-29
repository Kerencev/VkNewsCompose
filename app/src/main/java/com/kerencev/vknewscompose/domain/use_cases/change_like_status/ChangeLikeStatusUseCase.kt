package com.kerencev.vknewscompose.domain.use_cases.change_like_status

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.Flow

interface ChangeLikeStatusUseCase {

    operator fun invoke(newsModel: NewsModel): Flow<DataResult<NewsModel>>
}