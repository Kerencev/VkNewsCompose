package com.kerencev.vknewscompose.presentation.screens.profile_photos.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class ProfilePhotosViewState(
    val photos: List<PhotoModel> = emptyList(),
    val isPhotosLoading: Boolean = false,
    val errorMessage: String? = null,
    val photosTotalCount: Int = 0
) : VkState {

    fun setPhotos(photosModel: PhotosModel) = copy(
        photos = photosModel.photos,
        isPhotosLoading = false,
        errorMessage = null,
        photosTotalCount = photosModel.totalCount
    )

    fun loading() = copy(
        isPhotosLoading = true,
        errorMessage = null
    )

    fun error(message: String) = copy(
        isPhotosLoading = false,
        errorMessage = message
    )

}
