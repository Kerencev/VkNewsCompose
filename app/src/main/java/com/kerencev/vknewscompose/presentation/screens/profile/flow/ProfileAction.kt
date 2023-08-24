package com.kerencev.vknewscompose.presentation.screens.profile.flow

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class ProfileInputAction : VkAction {

    /**
     * Get profile data by user id
     */
    class GetProfile(val userId: Long) : ProfileInputAction()

    /**
     * Get all profile photos by user id
     */
    class GetProfilePhotos(val userId: Long) : ProfileInputAction()

    /**
     * Get wall posts by user id
     */
    class GetWall(val userId: Long) : ProfileInputAction()

    /**
     * Calculate the necessary UI parameters
     */
    class CalculateUiParams(
        val firstVisibleItem: Int? = null,
        val firstVisibleItemScrollOffset: Int? = null
    ) : ProfileInputAction()

    /**
     * Get all profile data by user id
     * profile, photos, wall posts
     */
    class RefreshProfileData(val userId: Long) : ProfileInputAction()
}

sealed class ProfileOutputAction : VkAction {

    /**
     * Set profile data to viewState
     */
    class SetProfile(val result: ProfileModel) : ProfileOutputAction()

    /**
     * Set profile photos to viewState
     */
    class SetProfilePhotos(val result: List<PhotoModel>) : ProfileOutputAction()

    /**
     * Add wall posts to viewState
     * @param wallItems - list of wall models
     * @param isItemsOver - Have the posts ended
     */
    class SetWall(val wallItems: List<NewsModel>, val isItemsOver: Boolean) : ProfileOutputAction()

    /**
     * Set profile loading to viewState
     */
    object ProfileLoading : ProfileOutputAction()

    /**
     * Set profile photos loading to viewState
     */
    object ProfilePhotosLoading : ProfileOutputAction()

    /**
     * Set wall loading to viewState
     */
    object WallLoading : ProfileOutputAction()

    /**
     * Set profile error to viewState
     */
    class ProfileError(val message: String) : ProfileOutputAction()

    /**
     * Set profile photos error to viewState
     */
    class ProfilePhotosError(val message: String) : ProfileOutputAction()

    /**
     * Set wall error to viewState
     */
    class WallError(val message: String) : ProfileOutputAction()

    /**
     * Set the necessary UI parameters
     * @param topBarAlpha - alpha of the Toolbar
     * @param blurBackgroundAlpha - alpha of the background under user's avatar
     * @param avatarAlpha - alpha of the user's avatar
     * @param avatarSize - size of the user's avatar
     */
    class SetUiParams(
        val topBarAlpha: Float,
        val blurBackgroundAlpha: Float,
        val avatarAlpha: Float,
        val avatarSize: Float
    ) : ProfileOutputAction()

    /**
     * When the swipe-refresh is used
     */
    object AllProfileDataRefreshing : ProfileOutputAction()

    /**
     * Used after swipe-refresh
     */
    class SetAllProfileData(
        val profile: ProfileModel,
        val photos: List<PhotoModel>,
        val wallItems: List<NewsModel>,
        val isWallItemsOver: Boolean,
    ) : ProfileOutputAction()
}

sealed class ProfileEffect : VkEffect {

    /**
     * Error after swipe-refresh
     */
    class AllProfileDataError(val message: String) : ProfileEffect()

    object None : ProfileEffect()

}