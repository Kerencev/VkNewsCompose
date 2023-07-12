package com.kerencev.vknewscompose.presentation.screens.profile.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkShot

sealed class ProfileShot : VkShot {

    class ShowErrorMessage(val message: String) : ProfileShot()

    object None : ProfileShot()

}
