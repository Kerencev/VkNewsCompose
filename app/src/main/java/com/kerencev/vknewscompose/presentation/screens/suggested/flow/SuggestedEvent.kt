package com.kerencev.vknewscompose.presentation.screens.suggested.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class SuggestedEvent : VkEvent {

    class GetData(val isRefreshing: Boolean) : SuggestedEvent()

}