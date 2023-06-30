package com.kerencev.vknewscompose.domain.use_cases.change_like_status

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEffect
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ChangeLikeStatusUseCaseImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : ChangeLikeStatusUseCase {

    @OptIn(FlowPreview::class)
    override fun invoke(newsModel: NewsModel): Flow<VkCommand> {
        return repository.changeLikeStatus(newsModel)
            .flatMapConcat { updatedModel ->
                flowOf(HomeOutputAction.ChangeLikeStatus(newsModel = updatedModel) as VkCommand)
            }
            .catch {
                emit(HomeEffect.LikeError(message = it.message.orEmpty()))
            }
    }
}