package com.kerencev.vknewscompose.domain.use_cases.change_like_status

import com.kerencev.vknewscompose.domain.entities.NewsModel

interface ChangeLikeStatusUseCase {

    suspend operator fun invoke(newsModel: NewsModel)
}