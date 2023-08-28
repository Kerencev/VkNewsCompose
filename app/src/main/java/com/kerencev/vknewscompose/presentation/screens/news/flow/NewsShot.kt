package com.kerencev.vknewscompose.presentation.screens.news.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkShot

sealed class NewsShot : VkShot {

    class ShowErrorMessage(val message: String) : NewsShot()

    object None : NewsShot()

}
