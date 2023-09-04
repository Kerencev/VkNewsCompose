package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.model.ProfileType
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileEffect
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetAllProfileDataFeatureImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : GetAllProfileDataFeature {

    override fun invoke(action: ProfileInputAction.RefreshProfileData): Flow<VkCommand> {
        return combine(
            getProfileByParams(action.profileParams),
            profileRepository.getProfilePhotos(userId = action.profileParams.id, isRefresh = true),
            profileRepository.getWallData(userId = action.profileParams.id, isRefresh = true)
        ) { profile, photos, wallModel ->
            ProfileOutputAction.SetAllProfileData(
                profile = profile,
                photos = photos,
                wallItems = wallModel.data,
                isWallItemsOver = wallModel.isItemsOver
            ) as VkCommand
        }
            .onStart { emit(ProfileOutputAction.AllProfileDataRefreshing(isRefreshing = true)) }
            .catch {
                emit(ProfileOutputAction.AllProfileDataRefreshing(isRefreshing = false))
                emit(ProfileEffect.AllProfileDataError(message = it.message.orEmpty()))
            }
    }

    private fun getProfileByParams(profileParams: ProfileParams) = when (profileParams.type) {
        ProfileType.USER -> profileRepository.getUserProfile(
            userId = profileParams.id,
            isRefresh = true
        )

        ProfileType.GROUP -> profileRepository.getGroupProfile(
            groupId = profileParams.id,
            isRefresh = true
        )
    }
}