package com.kerencev.vknewscompose.presentation.screens.suggested

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedEvent
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedInputAction
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedOutputAction
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedViewState
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.features.GetSuggestedFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuggestedViewModel @Inject constructor(
    private val getSuggestedFeature: GetSuggestedFeature
) : BaseViewModel<SuggestedEvent, SuggestedViewState, VkShot>() {

    override fun initState() = SuggestedViewState()

    override fun produceCommand(event: SuggestedEvent): VkCommand {
        return when (event) {
            is SuggestedEvent.GetData -> SuggestedInputAction.GetData(isRefreshing = event.isRefreshing)
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is SuggestedInputAction.GetData -> getSuggestedFeature(action)
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is SuggestedOutputAction.SetData -> setState { setData(action.result) }
            is SuggestedOutputAction.Loading -> setState { loading(action.isRefreshing) }
            is SuggestedOutputAction.Error -> setState { error(action.message) }
        }
    }
}