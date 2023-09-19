package com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerInputAction
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerOutputAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPostPhotosFeatureImpl @Inject constructor(
    private val repository: NewsFeedRepository
) : GetPostPhotosFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: PhotosPagerInputAction.GetNewsPostPhotos): Flow<VkCommand> {
        return repository.getPostPhotosById(action.newsModelId)
            .flatMapConcat { flowOf(PhotosPagerOutputAction.SetPostPhotos(it)) }
    }

}