package com.kerencev.vknewscompose.presentation.screens.profile.flow

import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class ProfileInputAction : VkAction {

    /**
     * Get profile data
     */
    object GetProfile : ProfileInputAction()

    /**
     * Get profile photos
     */
    object GetPhotos : ProfileInputAction()

    /**
     * Get wall posts
     */
    object GetWall : ProfileInputAction()
}

sealed class ProfileOutputAction : VkAction {

    /**
     * Set profile data to viewState
     */
    class SetProfile(val result: ProfileModel) : ProfileOutputAction()

    /**
     * Set profile photos to viewState
     */
    class SetPhotos(val result: List<PhotoModel>) : ProfileOutputAction()

    /**
     * Add wall posts to viewState
     */
    class SetWall(val result: WallModel) : ProfileOutputAction()

    /**
     * Set profile loading to viewState
     */
    object ProfileLoading : ProfileOutputAction()

    /**
     * Set photos loading to viewState
     */
    object PhotosLoading : ProfileOutputAction()

    /**
     * Set wall loading to viewState
     */
    object WallLoading : ProfileOutputAction()

    /**
     * Set profile error to viewState
     */
    class ProfileError(val message: String) : ProfileOutputAction()

    /**
     * Set photos error to viewState
     */
    class PhotosError(val message: String) : ProfileOutputAction()

    /**
     * Set wall error to viewState
     */
    class WallError(val message: String) : ProfileOutputAction()

    /**
     * Set the end of data message
     */
    object WallItemsIsOver : ProfileOutputAction()
}