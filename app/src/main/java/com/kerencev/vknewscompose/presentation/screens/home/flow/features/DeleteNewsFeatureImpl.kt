package com.kerencev.vknewscompose.presentation.screens.home.flow.features

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeleteNewsFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : DeleteNewsFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(action: HomeInputAction.DeleteNews, state: HomeViewState): Flow<VkCommand> {
        return repository.deleteNews(action.newsModel)
            .flatMapConcat {
                flowOf(HomeOutputAction.DeleteNews(newsModel = action.newsModel) as VkCommand)
            }
    }
}