package com.kerencev.vknewscompose.presentation.screens.profile_photos_pager

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.flow.ProfilePhotosPagerEvent
import com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.flow.ProfilePhotosPagerViewState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfilePhotosPagerViewModel @Inject constructor(
    private val getProfilePhotosFeature: GetProfilePhotosFeature
) : BaseViewModel<ProfilePhotosPagerEvent, ProfilePhotosPagerViewState, VkShot>() {

    init {
        send(ProfilePhotosPagerEvent.GetProfilePhotos)
    }

    override fun initState() = ProfilePhotosPagerViewState()

    override fun produceCommand(event: ProfilePhotosPagerEvent): VkCommand {
        return when (event) {
            is ProfilePhotosPagerEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfilePhotos -> getProfilePhotosFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfilePhotos -> setState { setPhotos(action.result) }
            is ProfileOutputAction.ProfilePhotosLoading -> setState { loading() }
            is ProfileOutputAction.ProfilePhotosError -> setState { error(action.message) }
        }
    }
}