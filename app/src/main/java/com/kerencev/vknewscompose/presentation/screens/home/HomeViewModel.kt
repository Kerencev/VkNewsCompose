package com.kerencev.vknewscompose.presentation.screens.home

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.mapper.mapToModel
import com.kerencev.vknewscompose.presentation.mapper.mapToUiModel
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEffect
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEvent
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeShot
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.DeleteNewsFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNewsFeature: GetNewsFeature,
    private val changeLikeStatusFeature: ChangeLikeStatusFeature,
    private val deleteNewsFeature: DeleteNewsFeature
) : BaseViewModel<HomeEvent, HomeViewState, HomeShot>() {

    private var firstVisibleItemIndex = 0

    override fun initState() = HomeViewState()

    override fun produceCommand(event: HomeEvent): VkCommand {
        return when (event) {
            is HomeEvent.GetNews -> HomeInputAction.GetNews(event.isRefresh)
            is HomeEvent.ChangeLikeStatus -> HomeInputAction.ChangeLikeStatus(
                event.newsModelUi.mapToModel()
            )

            is HomeEvent.DeleteNews -> HomeInputAction.DeleteNews(event.newsModelUi.mapToModel())
            is HomeEvent.OnErrorInvoked -> HomeEffect.None
            is HomeEvent.OnScrollToTop -> HomeOutputAction.OnScrollToTop
            is HomeEvent.OnUserScroll -> HomeOutputAction.OnUserScroll(event.firstVisibleItemIndex)
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is HomeInputAction.GetNews -> getNewsFeature(action, state())
            is HomeInputAction.ChangeLikeStatus -> changeLikeStatusFeature(action, state())
            is HomeInputAction.DeleteNews -> deleteNewsFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) {
        when (effect) {
            is HomeEffect.LikeError -> setShot { HomeShot.ShowErrorMessage(effect.message) }
            is HomeEffect.RefreshNewsError -> setShot { HomeShot.ShowErrorMessage(effect.message) }
            is HomeEffect.None -> setShot { HomeShot.None }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is HomeOutputAction.GetNewsSuccess -> {
                val data = action.result.map { it.mapToUiModel() }
                val newsItems = if (action.isRefresh) data else state().newsList + data
                setState { setNews(list = newsItems, scrollToTop = action.isRefresh) }
            }

            is HomeOutputAction.GetNewsLoading -> setState { loading() }
            is HomeOutputAction.GetNewsError -> setState { error() }
            is HomeOutputAction.ChangeLikeStatus -> setState {
                updateItem(action.newsModel.mapToUiModel())
            }

            is HomeOutputAction.DeleteNews -> setState { deleteItem(action.newsModel.mapToUiModel()) }
            is HomeOutputAction.GetNewsRefreshing -> setState { refreshing() }
            is HomeOutputAction.OnScrollToTop -> setState { onScrollToTop() }
            is HomeOutputAction.OnUserScroll -> {
                val isScrollToTopVisible = action.firstVisibleItemIndex in 1 until firstVisibleItemIndex &&
                        !state().isSwipeRefreshing
                setState { setScrollToTopVisible(isScrollToTopVisible) }
                firstVisibleItemIndex = action.firstVisibleItemIndex
            }
        }
    }
}