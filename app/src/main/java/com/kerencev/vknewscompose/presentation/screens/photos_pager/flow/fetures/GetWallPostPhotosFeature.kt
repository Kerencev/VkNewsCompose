package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerInputAction
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerViewState

/**
 * Feature for getting photos of the post from the user's wall
 */
interface GetWallPostPhotosFeature :
    VkFeature<PhotosPagerInputAction.GetWallPostPhotos, PhotosPagerViewState>