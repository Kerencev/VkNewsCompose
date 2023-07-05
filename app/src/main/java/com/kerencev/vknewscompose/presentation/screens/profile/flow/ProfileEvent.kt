package com.kerencev.vknewscompose.presentation.screens.profile.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class ProfileEvent : VkEvent {

    /**
     * Load profile data
     */
    object GetProfile : ProfileEvent()

    /**
     * Load profile photos
     */
    object GetPhotos : ProfileEvent()

    /**
     * Load wall posts
     */
    object GetWall : ProfileEvent()
}