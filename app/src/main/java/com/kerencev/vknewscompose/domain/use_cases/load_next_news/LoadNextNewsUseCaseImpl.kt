package com.kerencev.vknewscompose.domain.use_cases.load_next_news

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import javax.inject.Inject

class LoadNextNewsUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : LoadNextNewsUseCase {

    override suspend operator fun invoke() {
        return repository.loadNextNews()
    }

}