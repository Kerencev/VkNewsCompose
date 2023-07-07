package com.kerencev.vknewscompose.presentation.screens.home.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkShot

sealed class HomeShot : VkShot {

    class ShowErrorMessage(val message: String) : HomeShot()

    object None : HomeShot()

}
