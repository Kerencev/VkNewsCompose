package com.kerencev.vknewscompose.domain.use_cases.delete_news

import com.kerencev.vknewscompose.domain.entities.NewsModel

interface DeleteNewsUseCase {

    suspend operator fun invoke(newsModel: NewsModel)
}