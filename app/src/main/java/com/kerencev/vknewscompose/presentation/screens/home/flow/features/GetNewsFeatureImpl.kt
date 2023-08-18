package com.kerencev.vknewscompose.presentation.screens.home.flow.features

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEffect
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
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
    override fun invoke(action: HomeInputAction.GetNews, state: HomeViewState): Flow<VkCommand> {
        return repository.getNewsFeed(action.isRefresh)
            .flatMapConcat {
                flowOf(
                    HomeOutputAction.GetNewsSuccess(
                        result = it,
                        isRefresh = action.isRefresh
                    ) as VkCommand
                )
            }
            .onStart {
                if (action.isRefresh) emit(HomeOutputAction.GetNewsRefreshing)
                else emit(HomeOutputAction.GetNewsLoading)
            }
            .retryDefault()
            .catch {
                emit(HomeOutputAction.GetNewsError(it))
                if (action.isRefresh) emit(HomeEffect.RefreshNewsError(it.message.orEmpty()))
            }
    }
}