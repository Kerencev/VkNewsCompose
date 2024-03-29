package com.kerencev.vknewscompose.presentation.screens.suggested.flow

import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class SuggestedInputAction : VkAction {

    class GetData(val isRefreshing: Boolean) : SuggestedInputAction()
}

sealed class SuggestedOutputAction : VkAction {

    class SetData(val result: List<Profile>) : SuggestedOutputAction()

    class Loading(val isRefreshing: Boolean) : SuggestedOutputAction()

    class Error(val message: String) : SuggestedOutputAction()
}

sealed class SuggestedEffect : VkEffect {

    object ScrollToTop : SuggestedEffect()

    object None : SuggestedEffect()

}