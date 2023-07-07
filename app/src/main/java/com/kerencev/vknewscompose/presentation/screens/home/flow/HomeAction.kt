package com.kerencev.vknewscompose.presentation.screens.home.flow

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class HomeInputAction : VkAction {

    /**
     * Get news list
     * @param isRefresh - updating the list or not
     */
    class GetNews(val isRefresh: Boolean) : HomeInputAction()

    /**
     * Action when clicking on a like
     * @param newsModel - model that likes
     */
    class ChangeLikeStatus(val newsModel: NewsModel) : HomeInputAction()

    /**
     * Action when deleting a post
     * @param newsModel - model to be deleted
     */
    class DeleteNews(val newsModel: NewsModel) : HomeInputAction()

}

sealed class HomeOutputAction : VkAction {

    /**
     * Successful loading of the list
     * @param result - list of the items
     * @param isRefresh - updating the list or adding data
     */
    class GetNewsSuccess(val result: List<NewsModel>, val isRefresh: Boolean) : HomeOutputAction()

    /**
     * Loading data while scrolling
     */
    object GetNewsLoading : HomeOutputAction()

    /**
     * Emits when an error occurs while loading the list
     */
    class GetNewsError(val throwable: Throwable) : HomeOutputAction()

    /**
     * Emits when the status of a post's like changes
     * @param newsModel - model with an updated like status
     */
    class ChangeLikeStatus(val newsModel: NewsModel) : HomeOutputAction()

    /**
     * Emits after successful deletion of the post
     * @param newsModel - model that was deleted
     */
    class DeleteNews(val newsModel: NewsModel) : HomeOutputAction()

    /**
     * Loading data with swipe-to-refresh
     */
    object GetNewsRefreshing : HomeOutputAction()

    /**
     * Emits after scrolling up
     */
    object OnScrollToTop : HomeOutputAction()

    /**
     * Emits to find out whether to show popup for news updates
     * @param firstVisibleItemIndex - index of the first visible item to calculate the visibility of a popup
     */
    class OnUserScroll(val firstVisibleItemIndex: Int) : HomeOutputAction()

}

sealed class HomeEffect : VkEffect {

    /**
     * Occurrence of an error when liking
     */
    class LikeError(val message: String) : HomeEffect()

    object None : HomeEffect()

}