package com.kerencev.vknewscompose.domain.use_cases.change_like_status

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import javax.inject.Inject

class ChangeLikeStatusUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : ChangeLikeStatusUseCase {

    override operator fun invoke(newsModel: NewsModel) = repository.changeLikeStatus(newsModel)

}