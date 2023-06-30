package com.kerencev.vknewscompose.presentation.screens.home.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

sealed class HomeEvent : VkEvent {

    object GetNews : HomeEvent()

    class DeleteNews(val newsModelUi: NewsModelUi) : HomeEvent()

    class ChangeLikeStatus(val newsModelUi: NewsModelUi) : HomeEvent()

    object HideSnackBar : HomeEvent()

}
