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
 * @param profileState - Content with profile data or Loading or Error
 * @param friendsCount - Number of friends of this profile
 * @param profilePhotosState - Content with list of photos or Loading or Error
 * @param wallItems - List of wall posts
 * @param isWallLoading - Is wall posts loading
 * @param wallErrorMessage - Message when an error occurs during loading wall posts
 * @param isWallPostsOver - Flag indicating the end of the wall posts
 */
@Stable
data class ProfileViewState(
    val profileState: ContentState<ProfileModel> = ContentState.Loading,
    val friendsCount: Int = 0,
    val profilePhotosState: ContentState<List<PhotoModel>> = ContentState.Loading,
    val wallItems: List<NewsModelUi> = emptyList(),
    val isWallLoading: Boolean = true,
    val wallErrorMessage: String? = null,
    val isWallPostsOver: Boolean = false
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

    fun setWall(items: List<NewsModelUi>) = copy(
        wallItems = wallItems + items,
        isWallLoading = false,
        wallErrorMessage = null
    )

    fun wallLoading() = copy(
        isWallLoading = true,
        wallErrorMessage = null
    )

    fun wallError(message: String) = copy(
        isWallLoading = false,
        wallErrorMessage = message
    )

    fun wallPostsIsOver() = copy(
        isWallPostsOver = true
    )

}
