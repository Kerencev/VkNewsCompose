package com.kerencev.vknewscompose.presentation.screens.home.flow

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class HomeInputAction : VkAction {

    object GetNews : HomeInputAction()

    class ChangeLikeStatus(val newsModel: NewsModel) : HomeInputAction()

    class DeleteNews(val newsModel: NewsModel) : HomeInputAction()

}

sealed class HomeOutputAction : VkAction {

    class GetNewsSuccess(val result: List<NewsModel>) : HomeOutputAction()

    object GetNewsLoading : HomeOutputAction()

    class GetNewsError(val throwable: Throwable) : HomeOutputAction()

    class ChangeLikeStatus(val newsModel: NewsModel) : HomeOutputAction()

    class DeleteNews(val newsModel: NewsModel) : HomeOutputAction()

}

sealed class HomeEffect : VkEffect {

    class LikeError(val message: String) : HomeEffect()

    object None : HomeEffect()

}