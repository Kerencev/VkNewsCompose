package com.kerencev.vknewscompose.presentation.screens.news

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.mapper.mapToModel
import com.kerencev.vknewscompose.presentation.mapper.mapToUiModel
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEffect
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEvent
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsOutputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsShot
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsViewState
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val params: NewsParams,
    private val getNewsFeature: GetNewsFeature,
    private val changeLikeStatusFeature: ChangeLikeStatusFeature,
) : BaseViewModel<NewsEvent, NewsViewState, NewsShot>() {

    private var firstVisibleItemIndex = 0

    override fun initState() = NewsViewState()

    override fun produceCommand(event: NewsEvent): VkCommand {
        return when (event) {
            is NewsEvent.GetNews -> NewsInputAction.GetNews(
                newsType = params.type,
                isRefresh = event.isRefresh
            )

            is NewsEvent.ChangeLikeStatus -> NewsInputAction.ChangeLikeStatus(
                newsModel = event.newsModelUi.mapToModel()
            )

            is NewsEvent.OnErrorInvoked -> NewsEffect.None
            is NewsEvent.OnScrollToTop -> NewsOutputAction.OnScrollToTop
            is NewsEvent.OnUserScroll -> NewsOutputAction.OnUserScroll(event.firstVisibleItemIndex)
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is NewsInputAction.GetNews -> getNewsFeature(action, state())
            is NewsInputAction.ChangeLikeStatus -> changeLikeStatusFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) {
        when (effect) {
            is NewsEffect.LikeError -> setShot { NewsShot.ShowErrorMessage(effect.message) }
            is NewsEffect.RefreshNewsError -> setShot { NewsShot.ShowErrorMessage(effect.message) }
            is NewsEffect.None -> setShot { NewsShot.None }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is NewsOutputAction.GetNewsSuccess -> {
                val data = action.result.map { it.mapToUiModel() }
                setState { setNews(list = data, scrollToTop = action.isRefresh) }
            }

            is NewsOutputAction.GetNewsLoading -> setState { loading() }
            is NewsOutputAction.GetNewsError -> setState { error() }
            is NewsOutputAction.ChangeLikeStatus -> setState {
                updateItem(action.newsModel.mapToUiModel())
            }

            is NewsOutputAction.GetNewsRefreshing -> setState { refreshing() }
            is NewsOutputAction.OnScrollToTop -> setState { onScrollToTop() }
            is NewsOutputAction.OnUserScroll -> {
                val isScrollToTopVisible =
                    action.firstVisibleItemIndex in 1 until firstVisibleItemIndex &&
                            !state().isSwipeRefreshing
                setState { setScrollToTopVisible(isScrollToTopVisible) }
                firstVisibleItemIndex = action.firstVisibleItemIndex
            }
        }
    }
}