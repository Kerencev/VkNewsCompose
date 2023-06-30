package com.kerencev.vknewscompose.domain.use_cases.delete_news

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import javax.inject.Inject

class DeleteNewsUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : DeleteNewsUseCase {

    override operator fun invoke(newsModel: NewsModel) = repository.deleteNews(newsModel)

}