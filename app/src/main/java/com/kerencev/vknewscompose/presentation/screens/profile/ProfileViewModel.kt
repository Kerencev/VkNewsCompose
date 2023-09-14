package com.kerencev.vknewscompose.presentation.screens.profile

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.mapper.mapToModel
import com.kerencev.vknewscompose.presentation.mapper.mapToUiModel
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsEffect
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsOutputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEffect
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEvent
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileShot
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileParams: ProfileParams,
    private val getWallFeature: GetWallFeature,
    private val getProfileFeature: GetProfileFeature,
    private val getProfilePhotosFeature: GetProfilePhotosFeature,
    private val calculateProfileParamsFeature: CalculateProfileParamsFeature,
    private val getAllProfileDataFeature: GetAllProfileDataFeature,
    private val changeLikeStatusFeature: ChangeLikeStatusFeature,
) : BaseViewModel<ProfileEvent, ProfileViewState, ProfileShot>() {

    init {
        send(ProfileEvent.GetProfile)
        send(ProfileEvent.GetProfilePhotos)
        send(ProfileEvent.GetWall)
    }

    override fun initState() = ProfileViewState()

    override fun produceCommand(event: ProfileEvent): VkCommand {
        return when (event) {
            is ProfileEvent.GetProfile -> ProfileInputAction.GetProfile(profileParams)
            is ProfileEvent.GetProfilePhotos -> ProfileInputAction.GetProfilePhotos(profileParams.id)
            is ProfileEvent.GetWall -> ProfileInputAction.GetWall(profileParams.id)
            is ProfileEvent.OnUserScroll -> ProfileInputAction.CalculateUiParams(
                profileType = profileParams.type,
                firstVisibleItem = event.firstVisibleItem,
                firstVisibleItemScrollOffset = event.firstVisibleItemScrollOffset
            )

            is ProfileEvent.RefreshProfileData -> ProfileInputAction.RefreshProfileData(profileParams)
            is ProfileEvent.OnProfileErrorInvoked -> ProfileEffect.None
            is ProfileEvent.ChangeLikeStatus -> NewsInputAction.ChangeLikeStatus(
                event.newsModelUi.mapToModel()
            )
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is ProfileInputAction.GetProfile -> getProfileFeature(action)
            is ProfileInputAction.GetProfilePhotos -> getProfilePhotosFeature(action)
            is ProfileInputAction.GetWall -> getWallFeature(action)
            is ProfileInputAction.CalculateUiParams -> calculateProfileParamsFeature(action)
            is ProfileInputAction.RefreshProfileData -> getAllProfileDataFeature(action)
            is NewsInputAction.ChangeLikeStatus -> changeLikeStatusFeature(action)
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) {
        when (effect) {
            is ProfileEffect.AllProfileDataError -> {
                setShot { ProfileShot.ShowErrorMessage(effect.message) }
            }

            is ProfileEffect.None -> setShot { ProfileShot.None }
            is NewsEffect.LikeError -> setShot { ProfileShot.ShowErrorMessage(effect.message) }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is ProfileOutputAction.SetProfile -> setState { setProfile(action.result) }
            is ProfileOutputAction.ProfileLoading -> setState { profileLoading() }
            is ProfileOutputAction.ProfileError -> setState { profileError(action.message) }
            is ProfileOutputAction.SetProfilePhotos -> setState { setProfilePhotos(action.photos) }
            is ProfileOutputAction.ProfilePhotosLoading -> setState { profilePhotosLoading() }
            is ProfileOutputAction.ProfilePhotosError -> setState { profilePhotosError(action.message) }
            is ProfileOutputAction.SetWall -> setState {
                setWall(
                    items = action.wallItems.map { it.mapToUiModel() },
                    isItemsOver = action.isItemsOver
                )
            }

            is ProfileOutputAction.WallLoading -> setState { wallLoading() }
            is ProfileOutputAction.WallError -> setState { wallError(action.message) }
            is ProfileOutputAction.SetUiParams -> setState {
                setSizes(
                    topBarAlpha = action.topBarAlpha,
                    blurBackgroundAlpha = action.blurBackgroundAlpha,
                    avatarAlpha = action.avatarAlpha,
                    avatarSize = action.avatarSize
                )
            }

            is ProfileOutputAction.AllProfileDataRefreshing -> setState {
                allDataRefreshing(action.isRefreshing)
            }

            is ProfileOutputAction.SetAllProfileData -> setState {
                setAllData(
                    profile = action.profile,
                    photosModel = action.photos,
                    wallItems = action.wallItems.map { it.mapToUiModel() },
                    isWallItemsOver = action.isWallItemsOver
                )
            }
            is NewsOutputAction.ChangeLikeStatus -> setState {
                updateItem(action.newsModel.mapToUiModel())
            }
        }
    }
}