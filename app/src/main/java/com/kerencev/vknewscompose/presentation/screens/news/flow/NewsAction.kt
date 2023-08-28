package com.kerencev.vknewscompose.presentation.screens.news.flow

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.NewsType
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class NewsInputAction : VkAction {

    /**
     * Get news list by date
     * @param isRefresh - updating the list or not
     * @param newsType - type of news, for example: by date, recommended
     */
    class GetNews(val newsType: NewsType, val isRefresh: Boolean) : NewsInputAction()

    /**
     * Action when clicking on a like
     * @param newsModel - model that likes
     */
    class ChangeLikeStatus(val newsModel: NewsModel) : NewsInputAction()

}

sealed class NewsOutputAction : VkAction {

    /**
     * Successful loading of the list
     * @param result - list of the items
     * @param isRefresh - updating the list or adding data
     */
    class GetNewsSuccess(val result: List<NewsModel>, val isRefresh: Boolean) : NewsOutputAction()

    /**
     * Loading data while scrolling
     */
    object GetNewsLoading : NewsOutputAction()

    /**
     * Emits when an error occurs while loading the list
     */
    class GetNewsError(val throwable: Throwable) : NewsOutputAction()

    /**
     * Emits when the status of a post's like changes
     * @param newsModel - model with an updated like status
     */
    class ChangeLikeStatus(val newsModel: NewsModel) : NewsOutputAction()

    /**
     * Loading data with swipe-to-refresh
     */
    object GetNewsRefreshing : NewsOutputAction()

    /**
     * Emits after scrolling up
     */
    object OnScrollToTop : NewsOutputAction()

    /**
     * Emits to find out whether to show popup for news updates
     * @param firstVisibleItemIndex - index of the first visible item to calculate the visibility of a popup
     */
    class OnUserScroll(val firstVisibleItemIndex: Int) : NewsOutputAction()

}

sealed class NewsEffect : VkEffect {

    /**
     * Occurrence of an error when liking
     */
    class LikeError(val message: String) : NewsEffect()

    /**
     * Occurrence of an error when refreshing the news
     */
    class RefreshNewsError(val message: String) : NewsEffect()

    object None : NewsEffect()

}