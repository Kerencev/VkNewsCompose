package com.kerencev.vknewscompose.presentation.screens.home.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

sealed class HomeEvent : VkEvent {

    /**
     * Event for getting a list of news
     * @param isRefresh - updating the list or not
     */
    class GetNews(val isRefresh: Boolean = false) : HomeEvent()

    /**
     * Event for deleting item
     * @param newsModelUi - item to be deleted
     */
    class DeleteNews(val newsModelUi: NewsModelUi) : HomeEvent()

    /**
     * Event when clicking on a like
     * @param newsModelUi - item to be liked
     */
    class ChangeLikeStatus(val newsModelUi: NewsModelUi) : HomeEvent()

    /**
     * Positive SnackBar click event
     */
    object HideSnackBar : HomeEvent()

    /**
     * Event after scrolling up
     */
    object OnScrollToTop : HomeEvent()

    /**
     * Event when the user scrolls
     * @param firstVisibleItemIndex - index of the first visible item to calculate the visibility of a popup
     */
    class OnUserScroll(val firstVisibleItemIndex: Int) : HomeEvent()

}
