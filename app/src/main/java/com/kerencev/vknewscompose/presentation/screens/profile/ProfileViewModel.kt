package com.kerencev.vknewscompose.presentation.screens.profile

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getWallFeature: GetWallFeature,
    private val getProfileFeature: GetProfileFeature,
    private val getProfilePhotosFeature: GetProfilePhotosFeature,
    private val newsModelMapper: NewsModelMapper
) : BaseViewModel<ProfileEvent, ProfileViewState, VkShot>() {

    init {
        send(ProfileEvent.GetProfile)
        send(ProfileEvent.GetProfilePhotos)
        send(ProfileEvent.GetWall)
    }

    override fun initState() = ProfileViewState()

    override fun produceCommand(event: ProfileEvent): VkCommand {
        return when (event) {
            ProfileEvent.GetProfile -> ProfileInputAction.GetProfile
            ProfileEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos
            ProfileEvent.GetWall -> ProfileInputAction.GetWall
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfile -> getProfileFeature(action, state())
            is ProfileInputAction.GetProfilePhotos -> getProfilePhotosFeature(action, state())
            is ProfileInputAction.GetWall -> getWallFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfile -> setState { setProfile(action.result) }
            is ProfileOutputAction.ProfileLoading -> setState { profileLoading() }
            is ProfileOutputAction.ProfileError -> setState { profileError(action.message) }
            is ProfileOutputAction.SetProfilePhotos -> setState { setProfilePhotos(action.result) }
            is ProfileOutputAction.ProfilePhotosLoading -> setState { profilePhotosLoading() }
            is ProfileOutputAction.ProfilePhotosError -> setState { profilePhotosError(action.message) }
            is ProfileOutputAction.SetWall -> {
                setState { setWall(action.result.items.map { newsModelMapper.mapToUi(it) }) }
            }
            is ProfileOutputAction.WallLoading -> setState { wallLoading() }
            is ProfileOutputAction.WallError -> setState { wallError(action.message) }
            is ProfileOutputAction.WallItemsIsOver -> setState { wallPostsIsOver() }
        }
    }
}