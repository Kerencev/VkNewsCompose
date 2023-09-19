package com.kerencev.vknewscompose.presentation.screens.main.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkShot

sealed class MainShot : VkShot {

    class ShowSnackBar(val message: String) : MainShot()

    object None : MainShot()

}
