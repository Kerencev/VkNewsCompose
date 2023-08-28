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
 * @param scrollToTop - flag for scrolling to the top
 * @param isScrollToTopVisible - visibility of a popup about the fresh posts
 */
@Stable
data class NewsViewState(
    val newsList: List<NewsModelUi> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSwipeRefreshing: Boolean = false,
    val scrollToTop: Boolean = false,
    val isScrollToTopVisible: Boolean = false
) : VkState {

    fun setNews(list: List<NewsModelUi>, scrollToTop: Boolean) = copy(
        newsList = list,
        isLoading = false,
        isError = false,
        isSwipeRefreshing = false,
        scrollToTop = scrollToTop,
        isScrollToTopVisible = false
    )

    fun loading() = copy(
        isLoading = true,
        isError = false,
        scrollToTop = false
    )


    fun error() = copy(
        isLoading = false,
        isError = true,
        isSwipeRefreshing = false,
        scrollToTop = false
    )

    fun refreshing() = copy(
        isSwipeRefreshing = true,
        scrollToTop = false,
        isScrollToTopVisible = false
    )

    fun onScrollToTop() = copy(
        scrollToTop = false
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
