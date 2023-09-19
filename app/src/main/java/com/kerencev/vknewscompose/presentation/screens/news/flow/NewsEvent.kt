package com.kerencev.vknewscompose.presentation.screens.news.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

sealed class NewsEvent : VkEvent {

    /**
     * Event for getting a list of news
     * @param isRefresh - updating the list or not
     */
    class GetNews(val isRefresh: Boolean = false) : NewsEvent()

    /**
     * Event when clicking on a like
     * @param newsModelUi - item to be liked
     */
    class ChangeLikeStatus(val newsModelUi: NewsModelUi) : NewsEvent()

    /**
     * When we invoked error lambda
     */
    object OnErrorInvoked : NewsEvent()

    /**
     * Event after scrolling up
     */
    object OnScrollToTop : NewsEvent()

    /**
     * Event when the user scrolls
     * @param firstVisibleItemIndex - index of the first visible item to calculate the visibility of a popup
     */
    class OnUserScroll(val firstVisibleItemIndex: Int) : NewsEvent()

}
