package com.kerencev.vknewscompose.domain.use_cases.get_news

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetNewsUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : GetNewsUseCase {

    override operator fun invoke() = repository.getNewsFeed()

}