package com.kerencev.vknewscompose.presentation.screens.suggested.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkShot

sealed class SuggestedShot : VkShot {

    object ScrollToTop : SuggestedShot()

    object None : SuggestedShot()

}
