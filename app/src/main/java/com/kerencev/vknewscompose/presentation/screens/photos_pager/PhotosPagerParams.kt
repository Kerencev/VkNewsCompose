package com.kerencev.vknewscompose.presentation.screens.photos_pager

import com.kerencev.vknewscompose.presentation.model.PhotoType

data class PhotosPagerParams(
    val userId: Long,
    val photoType: PhotoType,
    val selectedPhotoNumber: Int,
    val newsModelId: Long,
)
