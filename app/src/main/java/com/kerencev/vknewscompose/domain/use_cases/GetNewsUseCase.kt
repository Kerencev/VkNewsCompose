package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetNewsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<NewsModel>> {
        return repository.getNewsFeed()
    }

}