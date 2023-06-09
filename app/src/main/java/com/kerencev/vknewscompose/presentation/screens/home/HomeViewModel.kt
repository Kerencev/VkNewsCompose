package com.kerencev.vknewscompose.presentation.screens.home

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEffect
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeEvent
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeInputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeOutputAction
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeShot
import com.kerencev.vknewscompose.presentation.screens.home.flow.HomeViewState
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.DeleteNewsFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val newsModelMapper: NewsModelMapper,
    private val getNewsFeature: GetNewsFeature,
    private val changeLikeStatusFeature: ChangeLikeStatusFeature,
    private val deleteNewsFeature: DeleteNewsFeature
) : BaseViewModel<HomeEvent, HomeViewState, HomeShot>() {

    override fun initState() = HomeViewState()
    private var firstVisibleItemIndex = 0

    override fun produceCommand(event: HomeEvent): VkCommand {
        return when (event) {
            is HomeEvent.GetNews -> HomeInputAction.GetNews(event.isRefresh)
            is HomeEvent.ChangeLikeStatus -> HomeInputAction.ChangeLikeStatus(
                newsModelMapper.mapToEntity(event.newsModelUi)
            )
            is HomeEvent.DeleteNews -> HomeInputAction.DeleteNews(
                newsModelMapper.mapToEntity(event.newsModelUi)
            )
            is HomeEvent.HideSnackBar -> HomeEffect.None
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
            is HomeEffect.None -> setShot { HomeShot.None }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is HomeOutputAction.GetNewsSuccess -> setState {
                val data = action.result.map { newsModelMapper.mapToUi(it) }
                if (action.isRefresh) refreshNews(data) else setNews(data)
            }

            is HomeOutputAction.GetNewsLoading -> setState { loading() }
            is HomeOutputAction.GetNewsError -> setState { error() }
            is HomeOutputAction.ChangeLikeStatus -> setState {
                updateItem(newsModelMapper.mapToUi(action.newsModel))
            }

            is HomeOutputAction.DeleteNews -> setState {
                deleteItem(newsModelMapper.mapToUi(action.newsModel))
            }

            is HomeOutputAction.GetNewsRefreshing -> setState { refreshing() }
            is HomeOutputAction.OnScrollToTop -> setState { onScrollToTop() }
            is HomeOutputAction.OnUserScroll -> {
                setState {
                    setScrollToTopVisible(
                        isScrollToTopVisible =
                        action.firstVisibleItemIndex in 1 until firstVisibleItemIndex
                    )
                }
                firstVisibleItemIndex = action.firstVisibleItemIndex
            }
        }
    }
}