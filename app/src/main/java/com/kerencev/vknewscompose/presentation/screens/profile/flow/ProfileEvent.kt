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

    /**
     * When the user scroll
     */
    class OnUserScroll(
        val firstVisibleItem: Int? = null,
        val firstVisibleItemScrollOffset: Int? = null
    ) : ProfileEvent()

    /**
     * When the user uses swipe-refresh
     */
    object RefreshProfileData : ProfileEvent()

    /**
     * When we invoked profile error lambda
     */
    object OnProfileErrorInvoked : ProfileEvent()
}