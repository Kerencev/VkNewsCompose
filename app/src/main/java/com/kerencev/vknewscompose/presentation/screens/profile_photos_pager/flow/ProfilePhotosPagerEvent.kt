package com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class ProfilePhotosPagerEvent : VkEvent {

    object GetProfilePhotos : ProfilePhotosPagerEvent()

}