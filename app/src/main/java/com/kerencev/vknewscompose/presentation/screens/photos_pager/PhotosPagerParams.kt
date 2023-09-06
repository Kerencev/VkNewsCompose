package com.kerencev.vknewscompose.presentation.screens.photos_pager

data class PhotosPagerParams(
    val userId: Long = 0,
    val photoType: PhotoType,
    val selectedPhotoNumber: Int,
    val newsModelId: Long = 0,
)

enum class PhotoType {
    PROFILE, WALL, NEWS
}
