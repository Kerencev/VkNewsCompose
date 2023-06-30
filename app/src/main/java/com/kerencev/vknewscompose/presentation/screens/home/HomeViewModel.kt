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
import com.kerencev.vknewscompose.domain.use_cases.change_like_status.ChangeLikeStatusUseCase
import com.kerencev.vknewscompose.domain.use_cases.delete_news.DeleteNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_news.GetNewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val newsModelMapper: NewsModelMapper,
    private val getNewsUseCase: GetNewsUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : BaseViewModel<HomeEvent, HomeViewState, HomeShot>() {

    override fun initState() = HomeViewState()

    /**
     * Обрабатываем входящий [event] и в зависимости от него определяем дальнейшие дейтсивя
     */
    override fun produceCommand(event: HomeEvent): VkCommand {
        return when (event) {
            is HomeEvent.GetNews -> HomeInputAction.GetNews
            is HomeEvent.ChangeLikeStatus -> HomeInputAction.ChangeLikeStatus(
                newsModelMapper.mapToEntity(event.newsModelUi)
            )

            is HomeEvent.DeleteNews -> HomeInputAction.DeleteNews(
                newsModelMapper.mapToEntity(event.newsModelUi)
            )

            is HomeEvent.HideSnackBar -> HomeEffect.None
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is HomeInputAction.GetNews -> getNewsUseCase()
            is HomeInputAction.ChangeLikeStatus -> changeLikeStatusUseCase(action.newsModel)
            is HomeInputAction.DeleteNews -> deleteNewsUseCase(action.newsModel)
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
                setNews(newsList + action.result.map { newsModelMapper.mapToUi(it) })
            }

            is HomeOutputAction.GetNewsLoading -> setState { loading() }
            is HomeOutputAction.GetNewsError -> setState { error() }
            is HomeOutputAction.ChangeLikeStatus -> setState {
                updateItem(newsModelMapper.mapToUi(action.newsModel))
            }

            is HomeOutputAction.DeleteNews -> setState {
                deleteItem(newsModelMapper.mapToUi(action.newsModel))
            }
        }
    }
}