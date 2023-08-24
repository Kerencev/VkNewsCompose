package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class PhotosPagerEvent : VkEvent {

    object GetProfilePhotos : PhotosPagerEvent()

    object GetNewsPostPhotos : PhotosPagerEvent()

    object GetWallPostPhotos : PhotosPagerEvent()

}