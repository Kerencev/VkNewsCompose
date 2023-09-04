package com.kerencev.vknewscompose.presentation.common.mvi.state

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class SearchViewState<T>(
    val items: List<T> = emptyList(),
    val isItemsOver: Boolean = false,
    val isSwipeRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val query: String = ""
) : VkState {

    fun setData(query: String, items: List<T>, isItemsOver: Boolean) =
        copy(
            items = items,
            isItemsOver = isItemsOver,
            isSwipeRefreshing = false,
            isLoading = false,
            errorMessage = null,
            query = query
        )

    fun setLoading(isRefresh: Boolean, query: String) = copy(
        isSwipeRefreshing = isRefresh,
        isLoading = !isRefresh,
        errorMessage = null,
        query = query
    )

    fun setError(message: String, query: String) = copy(
        isSwipeRefreshing = false,
        isLoading = false,
        errorMessage = message,
        query = query
    )
}