package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository

class LoadNextNewsUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() {
        return repository.loadNextNews()
    }

}