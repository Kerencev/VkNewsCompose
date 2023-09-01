package com.kerencev.vknewscompose.presentation.screens.suggested.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class SuggestedViewState(
    val items: List<Profile> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSwipeRefreshing: Boolean = false,
) : VkState {

    fun setData(data: List<Profile>) = copy(
        items = data,
        isLoading = false,
        isError = false,
        isSwipeRefreshing = false,
    )

    fun loading(isSwipeRefreshing: Boolean) = copy(
        isLoading = true,
        isError = false,
        isSwipeRefreshing = isSwipeRefreshing
    )


    fun error() = copy(
        isLoading = false,
        isError = true,
        isSwipeRefreshing = false,
    )

}
