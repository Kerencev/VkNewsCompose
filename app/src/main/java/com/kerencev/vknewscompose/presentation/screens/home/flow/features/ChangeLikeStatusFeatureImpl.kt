package com.kerencev.vknewscompose.presentation.screens.home.flow.features

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEffect
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ChangeLikeStatusFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : ChangeLikeStatusFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: HomeInputAction.ChangeLikeStatus,
        state: HomeViewState
    ): Flow<VkCommand> {
        return repository.changeLikeStatus(action.newsModel)
            .flatMapConcat { updatedModel ->
                flowOf(HomeOutputAction.ChangeLikeStatus(newsModel = updatedModel) as VkCommand)
            }
            .retryDefault()
            .catch { emit(HomeEffect.LikeError(message = it.message.orEmpty())) }
    }
}