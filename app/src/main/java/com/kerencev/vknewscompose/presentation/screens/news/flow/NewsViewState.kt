package com.kerencev.vknewscompose.presentation.screens.news.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.presentation.common.mvi.VkState
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

/**
 * State for the HomeScreen
 * @param newsList - list of content items
 * @param isLoading - list loading flag
 * @param isError - flag for displaying an error that occurred when loading the list
 * @param isSwipeRefreshing - flag for displaying swipe-to-refresh icon
 * @param isScrollToTopVisible - visibility of a popup about the fresh posts
 */
@Stable
data class NewsViewState(
    val newsList: List<NewsModelUi> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSwipeRefreshing: Boolean = false,
    val isScrollToTopVisible: Boolean = false
) : VkState {

    fun setNews(list: List<NewsModelUi>) = copy(
        newsList = list,
        isLoading = false,
        isError = false,
        isSwipeRefreshing = false,
        isScrollToTopVisible = false
    )

    fun loading() = copy(
        isLoading = true,
        isError = false,
    )


    fun error() = copy(
        isLoading = false,
        isError = true,
        isSwipeRefreshing = false,
    )

    fun refreshing() = copy(
        isSwipeRefreshing = true,
        isScrollToTopVisible = false
    )

    fun setScrollToTopVisible(isScrollToTopVisible: Boolean) = copy(
        isScrollToTopVisible = isScrollToTopVisible
    )

    fun updateItem(updatedItem: NewsModelUi): NewsViewState {
        val data = newsList.toMutableList()
        val index = data.indexOfFirst { it.id == updatedItem.id }
        if (index == -1) return this
        data[index] = updatedItem
        return copy(newsList = data.toList())
    }

}
