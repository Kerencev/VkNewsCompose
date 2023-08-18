package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class PhotosPagerViewState(
    val photosState: ContentState<List<PhotoModel>> = ContentState.Loading,
    val isToolbarVisible: Boolean = true
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

    fun setToolbarVisibility(isVisible: Boolean) = copy(
        isToolbarVisible = isVisible
    )

}
