package com.kerencev.vknewscompose.domain.use_cases.get_news

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetNewsUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : GetNewsUseCase {

    @OptIn(FlowPreview::class)
    override fun invoke(): Flow<VkCommand> {
        return repository.getNewsFeed()
            .flatMapConcat { flowOf(HomeOutputAction.GetNewsSuccess(it) as VkCommand) }
            .onStart { emit(HomeOutputAction.GetNewsLoading) }
            .catch { emit(HomeOutputAction.GetNewsError(it)) }
    }
}