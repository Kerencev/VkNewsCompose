package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository

class DeleteNewsUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(newsModel: NewsModel) {
        return repository.deleteNews(newsModel)
    }

}