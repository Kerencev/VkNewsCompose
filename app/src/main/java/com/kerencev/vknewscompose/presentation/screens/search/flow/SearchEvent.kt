package com.kerencev.vknewscompose.presentation.screens.search.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class SearchEvent : VkEvent {

    class Search(val query: String) : SearchEvent()

    object GetNextPage : SearchEvent()

    object Refresh : SearchEvent()

    object Retry : SearchEvent()
}