package com.kerencev.vknewscompose.presentation.screens.profile.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.mvi.VkState
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

/**
 * State for the ProfileScreen
 *
 * @param profileState - content with profile data or Loading or Error
 * @param friendsCount - number of friends of this profile
 * @param profilePhotosState - content with list of photos or Loading or Error
 * @param wallItems - list of wall posts
 * @param isWallLoading - is wall posts loading
 * @param wallErrorMessage - message when an error occurs during loading wall posts
 * @param isWallPostsOver - flag indicating the end of the wall posts
 * @param topBarAlpha - alpha of the Toolbar
 * @param blurBackgroundAlpha - alpha of the background under user's avatar
 * @param avatarAlpha - alpha of the user's avatar
 * @param avatarSize - size of the user's avatar
 * @param isSwipeRefreshing - swipe-refresh displaying
 */
@Stable
data class ProfileViewState(
    val profileState: ContentState<ProfileModel> = ContentState.Loading,
    val friendsCount: Int = 0,
    val profilePhotosState: ContentState<List<PhotoModel>> = ContentState.Loading,
    val wallItems: List<NewsModelUi> = emptyList(),
    val isWallLoading: Boolean = true,
    val wallErrorMessage: String? = null,
    val isWallPostsOver: Boolean = false,
    val topBarAlpha: Float = 0f,
    val blurBackgroundAlpha: Float = 0.5f,
    val avatarAlpha: Float = 1f,
    val avatarSize: Float = 100f,
    val isSwipeRefreshing: Boolean = false,
) : VkState {

    fun setProfile(profileModel: ProfileModel) = copy(
        profileState = ContentState.Content(profileModel),
        friendsCount = profileModel.friendsCount
    )

    fun profileLoading() = copy(
        profileState = ContentState.Loading
    )

    fun profileError(message: String) = copy(
        profileState = ContentState.Error(message)
    )

    fun setProfilePhotos(photos: List<PhotoModel>) = copy(
        profilePhotosState = ContentState.Content(photos),
    )

    fun profilePhotosLoading() = copy(
        profilePhotosState = ContentState.Loading
    )

    fun profilePhotosError(message: String) = copy(
        profilePhotosState = ContentState.Error(message)
    )

    fun setWall(items: List<NewsModelUi>, isItemsOver: Boolean) = copy(
        wallItems = items,
        isWallLoading = false,
        wallErrorMessage = null,
        isWallPostsOver = isItemsOver
    )

    fun wallLoading() = copy(
        isWallLoading = true,
        wallErrorMessage = null
    )

    fun wallError(message: String) = copy(
        isWallLoading = false,
        wallErrorMessage = message,
    )

    fun setSizes(
        topBarAlpha: Float,
        blurBackgroundAlpha: Float,
        avatarAlpha: Float,
        avatarSize: Float
    ) = copy(
        topBarAlpha = topBarAlpha,
        blurBackgroundAlpha = blurBackgroundAlpha,
        avatarAlpha = avatarAlpha,
        avatarSize = avatarSize
    )

    fun allDataRefreshing(isSwipeRefreshing: Boolean) = copy(
        isSwipeRefreshing = isSwipeRefreshing,
    )

    fun setAllData(
        profile: ProfileModel,
        photos: List<PhotoModel>,
        wallItems: List<NewsModelUi>,
        isWallItemsOver: Boolean
    ) = copy(
        profileState = ContentState.Content(profile),
        friendsCount = profile.friendsCount,
        profilePhotosState = ContentState.Content(photos),
        wallItems = wallItems,
        isWallLoading = false,
        wallErrorMessage = null,
        isWallPostsOver = isWallItemsOver,
        isSwipeRefreshing = false
    )

}
