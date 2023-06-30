package com.kerencev.vknewscompose.domain.use_cases.delete_news

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeleteNewsUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : DeleteNewsUseCase {

    @OptIn(FlowPreview::class)
    override fun invoke(newsModel: NewsModel): Flow<VkCommand> {
        return repository.deleteNews(newsModel)
            .flatMapConcat {
                flowOf(HomeOutputAction.DeleteNews(newsModel = newsModel) as VkCommand)
            }
    }

}