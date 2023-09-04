package com.kerencev.vknewscompose.presentation.screens.search

import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.common.mvi.state.SearchViewState
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchEvent
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchInputAction
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchOutputAction
import com.kerencev.vknewscompose.presentation.screens.search.flow.features.SearchFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchFeature: SearchFeature
) : BaseViewModel<SearchEvent, SearchViewState<Profile>, VkShot>() {

    override fun initState() = SearchViewState<Profile>()

    override fun produceCommand(event: SearchEvent): VkCommand {
        return when (event) {
            is SearchEvent.Search -> handleSearchEvent(event)
            is SearchEvent.GetNextPage -> SearchInputAction.GetData(
                query = state().query,
                isRefresh = false
            )

            is SearchEvent.Refresh -> SearchInputAction.GetData(
                query = state().query,
                isRefresh = true
            )

            is SearchEvent.Retry -> SearchInputAction.GetData(
                query = state().query,
                isRefresh = false
            )
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is SearchInputAction.GetData -> searchFeature(action)
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is SearchOutputAction.SetData -> setState {
                setData(query = action.query, items = action.data, isItemsOver = action.isItemsOver)
            }

            is SearchOutputAction.Loading -> setState {
                setLoading(isRefresh = action.isRefresh, query = action.query)
            }

            is SearchOutputAction.Error -> setState {
                setError(message = action.message, query = action.query)
            }
        }
    }

    private fun handleSearchEvent(event: SearchEvent.Search): VkAction {
        return if (event.query == state().query) {
            SearchOutputAction.SetData(
                query = event.query,
                data = state().items,
                isItemsOver = state().isItemsOver
            )
        } else {
            SearchInputAction.GetData(
                query = event.query,
                isRefresh = true
            )
        }
    }

}