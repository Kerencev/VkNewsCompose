package com.kerencev.vknewscompose.domain.use_cases.get_news

import com.kerencev.vknewscompose.domain.entities.NewsModel
import kotlinx.coroutines.flow.StateFlow

interface GetNewsUseCase {

    operator fun invoke(): StateFlow<List<NewsModel>>
}