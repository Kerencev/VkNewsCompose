package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEffect
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetAllProfileDataFeatureImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : GetAllProfileDataFeature {

    override fun invoke(
        action: ProfileInputAction.RefreshProfileData,
        state: ProfileViewState
    ): Flow<VkCommand> {
        return combine(
            profileRepository.getProfile(userId = action.userId, isRefresh = true),
            profileRepository.getProfilePhotos(userId = action.userId, isRefresh = true),
            profileRepository.getWallData(userId = action.userId, isRefresh = true)
        ) { profile, photos, wallModel ->
            ProfileOutputAction.SetAllProfileData(
                profile = profile,
                photos = photos,
                wallItems = wallModel.items,
                isWallItemsOver = wallModel.isItemsOver
            ) as VkCommand
        }
            .onStart { emit(ProfileOutputAction.AllProfileDataRefreshing) }
            .catch { emit(ProfileEffect.AllProfileDataError(message = it.message.orEmpty())) }
    }
}