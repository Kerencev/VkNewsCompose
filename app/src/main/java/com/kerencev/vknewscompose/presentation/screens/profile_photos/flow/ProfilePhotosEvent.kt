package com.kerencev.vknewscompose.presentation.screens.profile_photos.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class ProfilePhotosEvent : VkEvent {

    object GetProfilePhotos : ProfilePhotosEvent()

}