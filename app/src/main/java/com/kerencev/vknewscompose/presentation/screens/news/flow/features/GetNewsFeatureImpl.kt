package com.kerencev.vknewscompose.presentation.screens.news.flow.features

import com.kerencev.vknewscompose.domain.entities.NewsType
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEffect
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsOutputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetNewsFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : GetNewsFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: NewsInputAction.GetNews, state: NewsViewState): Flow<VkCommand> {
        return when (action.newsType) {
            NewsType.BY_DATE -> repository.getNewsFeed(isRefresh = action.isRefresh)
            else -> repository.getRecommended(isRefresh = action.isRefresh)
        }
            .flatMapConcat {
                flowOf(
                    NewsOutputAction.GetNewsSuccess(
                        result = it,
                        isRefresh = action.isRefresh
                    ) as VkCommand
                )
            }
            .onStart {
                if (action.isRefresh) emit(NewsOutputAction.GetNewsRefreshing)
                else emit(NewsOutputAction.GetNewsLoading)
            }
            .retryDefault()
            .catch {
                emit(NewsOutputAction.GetNewsError(it))
                if (action.isRefresh) emit(NewsEffect.RefreshNewsError(it.message.orEmpty()))
            }
    }
}