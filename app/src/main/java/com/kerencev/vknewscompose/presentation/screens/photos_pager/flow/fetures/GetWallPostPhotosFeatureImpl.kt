package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerInputAction
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerOutputAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetWallPostPhotosFeatureImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : GetWallPostPhotosFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: PhotosPagerInputAction.GetWallPostPhotos): Flow<VkCommand> {
        return profileRepository.getWallItemPhotos(
            userId = action.userId,
            itemId = action.newsModelId
        )
            .flatMapConcat { photos ->
                flowOf(PhotosPagerOutputAction.SetPostPhotos(photos))
            }
    }
}