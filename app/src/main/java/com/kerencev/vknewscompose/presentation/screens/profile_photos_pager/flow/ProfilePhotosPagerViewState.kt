package com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class ProfilePhotosPagerViewState(
    val photosState: ContentState<List<PhotoModel>> = ContentState.Loading
) : VkState {

    fun setPhotos(photos: List<PhotoModel>) = copy(
        photosState = ContentState.Content(photos)
    )

    fun loading() = copy(
        photosState = ContentState.Loading
    )

    fun error(message: String) = copy(
        photosState = ContentState.Error(message)
    )

}
