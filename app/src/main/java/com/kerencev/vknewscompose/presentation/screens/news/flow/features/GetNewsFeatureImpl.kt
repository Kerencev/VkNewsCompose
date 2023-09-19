package com.kerencev.vknewscompose.presentation.screens.news.flow.features

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.news.NewsType
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEffect
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetNewsFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : GetNewsFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(action: NewsInputAction.GetNews): Flow<VkCommand> {
        return getNewsByType(action.newsType, action.isRefresh)
            .flatMapConcat {
                flow {
                    emit(NewsOutputAction.GetNewsSuccess(result = it))
                    if (action.isRefresh) emit(NewsEffect.ScrollToTop)
                }
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

    private fun getNewsByType(newsType: NewsType, isRefresh: Boolean) = when (newsType) {
        NewsType.NEWS -> repository.getNewsFeed(isRefresh = isRefresh)
        NewsType.RECOMMENDATION -> repository.getRecommended(isRefresh = isRefresh)
    }
}