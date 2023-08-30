package com.kerencev.vknewscompose.presentation.screens.news.flow.features

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEffect
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsOutputAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ChangeLikeStatusFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : ChangeLikeStatusFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: NewsInputAction.ChangeLikeStatus): Flow<VkCommand> {
        return repository.changeLikeStatus(action.newsModel)
            .flatMapConcat { updatedModel ->
                flowOf(NewsOutputAction.ChangeLikeStatus(newsModel = updatedModel) as VkCommand)
            }
            .retryDefault()
            .catch { emit(NewsEffect.LikeError(message = it.message.orEmpty())) }
    }
}