package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow

import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction

sealed class PhotosPagerInputAction : VkAction {

    class GetNewsPostPhotos(val newsModelId: Long) : PhotosPagerInputAction()

    class GetWallPostPhotos(val userId: Long, val newsModelId: Long) : PhotosPagerInputAction()

}

sealed class PhotosPagerOutputAction : VkAction {

    class SetPostPhotos(val result: List<PhotoModel>) : PhotosPagerOutputAction()

}