package com.kerencev.vknewscompose.presentation.screens.profile_photos.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class ProfilePhotosInputAction : VkAction {

    object GetPhotos : ProfilePhotosInputAction()

}