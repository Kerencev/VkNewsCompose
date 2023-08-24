package com.kerencev.vknewscompose.presentation.screens.profile_photos

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile_photos.flow.ProfilePhotosEvent
import com.kerencev.vknewscompose.presentation.screens.profile_photos.flow.ProfilePhotosViewState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfilePhotosViewModel @Inject constructor(
    private val getProfilePhotosFeature: GetProfilePhotosFeature
) : BaseViewModel<ProfilePhotosEvent, ProfilePhotosViewState, VkShot>() {

    init {
        send(ProfilePhotosEvent.GetProfilePhotos)
    }

    override fun initState() = ProfilePhotosViewState()

    override fun produceCommand(event: ProfilePhotosEvent): VkCommand {
        return when (event) {
            is ProfilePhotosEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos(0)
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
        }
    }
}