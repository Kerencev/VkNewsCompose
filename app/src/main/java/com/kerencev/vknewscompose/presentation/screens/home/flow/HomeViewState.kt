package com.kerencev.vknewscompose.presentation.screens.home.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.extensions.removeElement
import com.kerencev.vknewscompose.presentation.common.mvi.VkState
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

@Stable
data class HomeViewState(
    val newsList: List<NewsModelUi> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : VkState {

    fun setNews(list: List<NewsModelUi>) = copy(
        newsList = list,
        isLoading = false,
        isError = false
    )

    fun loading() = copy(
        isLoading = true,
        isError = false
    )


    fun error() = copy(
        isLoading = false,
        isError = true
    )

    fun updateItem(updatedItem: NewsModelUi): HomeViewState {
        val data = newsList.toMutableList()
        val index = data.indexOfFirst { it.id == updatedItem.id }
        if (index == -1) return this
        data[index] = updatedItem
        return copy(newsList = data.toList())
    }

    fun deleteItem(newsModelUi: NewsModelUi): HomeViewState {
        return copy(newsList = this.newsList.toMutableList().apply { remove(newsModelUi) }.toList())
    }

}
