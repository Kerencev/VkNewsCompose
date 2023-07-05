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
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getWallFeature: GetWallFeature,
    private val getProfileFeature: GetProfileFeature,
    private val getPhotosFeature: GetPhotosFeature,
    private val newsModelMapper: NewsModelMapper
) : BaseViewModel<ProfileEvent, ProfileViewState, VkShot>() {

    init {
        send(ProfileEvent.GetProfile)
        send(ProfileEvent.GetPhotos)
        send(ProfileEvent.GetWall)
    }

    override fun initState() = ProfileViewState()

    override fun produceCommand(event: ProfileEvent): VkCommand {
        return when (event) {
            ProfileEvent.GetProfile -> ProfileInputAction.GetProfile
            ProfileEvent.GetPhotos -> ProfileInputAction.GetPhotos
            ProfileEvent.GetWall -> ProfileInputAction.GetWall
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfile -> getProfileFeature(action, state())
            is ProfileInputAction.GetPhotos -> getPhotosFeature(action, state())
            is ProfileInputAction.GetWall -> getWallFeature(action, state())
            else -> null
        }
            ?.flowOn(Dispatchers.IO)
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfile -> setState { setProfile(action.result) }
            is ProfileOutputAction.ProfileLoading -> setState { profileLoading() }
            is ProfileOutputAction.ProfileError -> setState { profileError(action.message) }
            is ProfileOutputAction.SetPhotos -> setState { setPhotos(action.result) }
            is ProfileOutputAction.PhotosLoading -> setState { photosLoading() }
            is ProfileOutputAction.PhotosError -> setState { photosError(action.message) }
            is ProfileOutputAction.SetWall -> {
                setState { setWall(action.result.items.map { newsModelMapper.mapToUi(it) }) }
            }
            is ProfileOutputAction.WallLoading -> setState { wallLoading() }
            is ProfileOutputAction.WallError -> setState { wallError(action.message) }
            is ProfileOutputAction.WallItemsIsOver -> setState { wallPostsIsOver() }
        }
    }
}