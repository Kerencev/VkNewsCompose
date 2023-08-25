package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.data.repository.ProfileRepositoryImpl
import com.kerencev.vknewscompose.domain.entities.Photo
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
import com.kerencev.vknewscompose.domain.entities.getDummyPhotos
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class PhotosPagerViewState(
    val photos: List<Photo> = emptyList(),
    val isPhotosLoading: Boolean = false,
    val errorMessage: String? = null,
    val photosTotalCount: Int = 0,
    val isPhotosOver: Boolean = false
) : VkState {

    fun setPhotos(photosModel: PhotosModel) = copy(
        photos = photosModel.photos,
        isPhotosLoading = false,
        errorMessage = null,
        photosTotalCount = photosModel.totalCount,
        isPhotosOver = photosModel.photos.size == photosModel.totalCount
    )

    fun loading() = copy(
        photos = photos + getDummyPhotos(ProfileRepositoryImpl.PHOTOS_PAGE_SIZE),
        isPhotosLoading = true,
        errorMessage = null
    )

    fun error(message: String) = copy(
        photos = photos.filterIsInstance<PhotoModel>(),
        isPhotosLoading = false,
        errorMessage = message
    )

}
