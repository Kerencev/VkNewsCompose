package com.kerencev.vknewscompose.presentation.screens.profile.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class ProfileEvent : VkEvent {

    /**
     * Load profile data
     */
    object GetProfile : ProfileEvent()

    /**
     * Load all profile photos data
     */
    object GetProfilePhotos : ProfileEvent()

    /**
     * Load wall posts
     */
    object GetWall : ProfileEvent()
}