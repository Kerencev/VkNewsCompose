package com.kerencev.vknewscompose.presentation.screens.profile

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEffect
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getWallFeature: GetWallFeature,
    private val getProfileFeature: GetProfileFeature,
    private val getProfilePhotosFeature: GetProfilePhotosFeature,
    private val calculateProfileParamsFeature: CalculateProfileParamsFeature,
    private val getAllProfileDataFeature: GetAllProfileDataFeature,
    private val newsModelMapper: NewsModelMapper
) : BaseViewModel<ProfileEvent, ProfileViewState, ProfileShot>() {

    init {
        send(ProfileEvent.GetProfile)
        send(ProfileEvent.GetProfilePhotos)
        send(ProfileEvent.GetWall)
    }

    override fun initState() = ProfileViewState()

    override fun produceCommand(event: ProfileEvent): VkCommand {
        return when (event) {
            is ProfileEvent.GetProfile -> ProfileInputAction.GetProfile
            is ProfileEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos
            is ProfileEvent.GetWall -> ProfileInputAction.GetWall
            is ProfileEvent.OnUserScroll -> ProfileInputAction.CalculateUiParams(
                firstVisibleItem = event.firstVisibleItem,
                firstVisibleItemScrollOffset = event.firstVisibleItemScrollOffset
            )
            is ProfileEvent.RefreshProfileData -> ProfileInputAction.RefreshProfileData
            is ProfileEvent.HideErrorSnackBar -> ProfileEffect.None
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfile -> getProfileFeature(action, state())
            is ProfileInputAction.GetProfilePhotos -> getProfilePhotosFeature(action, state())
            is ProfileInputAction.GetWall -> getWallFeature(action, state())
            is ProfileInputAction.CalculateUiParams -> calculateProfileParamsFeature(action, state())
            is ProfileInputAction.RefreshProfileData -> getAllProfileDataFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) {
        when (effect) {
            is ProfileEffect.AllProfileDataError -> {
                setState { allDataRefreshing(false) }
                setShot { ProfileShot.ShowErrorMessage(effect.message) }
            }
            is ProfileEffect.None -> setShot { ProfileShot.None }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfile -> setState { setProfile(action.result) }
            is ProfileOutputAction.ProfileLoading -> setState { profileLoading() }
            is ProfileOutputAction.ProfileError -> setState { profileError(action.message) }
            is ProfileOutputAction.SetProfilePhotos -> setState { setProfilePhotos(action.result) }
            is ProfileOutputAction.ProfilePhotosLoading -> setState { profilePhotosLoading() }
            is ProfileOutputAction.ProfilePhotosError -> setState { profilePhotosError(action.message) }
            is ProfileOutputAction.SetWall -> setState {
                setWall(
                    items = action.wallItems.map { newsModelMapper.mapToUi(it) },
                    isItemsOver = action.isItemsOver
                )
            }
            is ProfileOutputAction.WallLoading -> setState { wallLoading() }
            is ProfileOutputAction.WallError -> setState { wallError(action.message) }
            is ProfileOutputAction.SetUiParams -> {
                setState {
                    setSizes(
                        topBarAlpha = action.topBarAlpha,
                        blurBackgroundAlpha = action.blurBackgroundAlpha,
                        avatarAlpha = action.avatarAlpha,
                        avatarSize = action.avatarSize
                    )
                }
            }
            is ProfileOutputAction.AllProfileDataRefreshing -> setState {
                allDataRefreshing(true)
            }
            is ProfileOutputAction.SetAllProfileData -> setState {
                setAllData(
                    profile = action.profile,
                    photos = action.photos,
                    wallItems = action.wallItems.map { newsModelMapper.mapToUi(it) },
                    isWallItemsOver = action.isWallItemsOver
                )
            }
        }
    }
}