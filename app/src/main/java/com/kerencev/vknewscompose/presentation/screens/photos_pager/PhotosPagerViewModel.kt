package com.kerencev.vknewscompose.presentation.screens.photos_pager

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.model.PhotoType
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerEvent
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerInputAction
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerOutputAction
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.PhotosPagerViewState
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosPagerViewModel @Inject constructor(
    private val params: PhotosPagerParams,
    private val getProfilePhotosFeature: GetProfilePhotosFeature,
    private val getPostPhotosFeature: GetPostPhotosFeature,
    private val getWallPostPhotosFeature: GetWallPostPhotosFeature
) : BaseViewModel<PhotosPagerEvent, PhotosPagerViewState, VkShot>() {

    init {
        when (params.photoType) {
            PhotoType.PROFILE -> send(PhotosPagerEvent.GetProfilePhotos)
            PhotoType.NEWS -> send(PhotosPagerEvent.GetNewsPostPhotos)
            PhotoType.WALL -> send(PhotosPagerEvent.GetWallPostPhotos)
        }
    }

    override fun initState() = PhotosPagerViewState()

    override fun produceCommand(event: PhotosPagerEvent): VkCommand {
        return when (event) {
            is PhotosPagerEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos(
                userId = params.userId
            )

            is PhotosPagerEvent.GetNewsPostPhotos -> PhotosPagerInputAction.GetNewsPostPhotos(
                newsModelId = params.newsModelId
            )

            is PhotosPagerEvent.GetWallPostPhotos -> PhotosPagerInputAction.GetWallPostPhotos(
                userId = params.userId,
                newsModelId = params.newsModelId
            )
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfilePhotos -> getProfilePhotosFeature(action, state())
            is PhotosPagerInputAction.GetNewsPostPhotos -> getPostPhotosFeature(action, state())
            is PhotosPagerInputAction.GetWallPostPhotos -> getWallPostPhotosFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfilePhotos -> setState { setPhotos(action.photos.photos) }
            is ProfileOutputAction.ProfilePhotosLoading -> setState { loading() }
            is ProfileOutputAction.ProfilePhotosError -> setState { error(action.message) }
            is PhotosPagerOutputAction.SetPostPhotos -> setState { setPhotos(action.result) }
            is PhotosPagerOutputAction.SetToolBarVisibility -> setState {
                setToolbarVisibility(action.isVisible)
            }
        }
    }
}