package com.kerencev.vknewscompose.presentation.screens.profile_photos.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class ProfilePhotosViewState(
    val photos: List<PhotoModel> = emptyList()
) : VkState {

    fun setPhotos(photos: List<PhotoModel>) = copy(
        photos = photos
    )

}
