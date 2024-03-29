package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetProfileFeatureImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetProfileFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: ProfileInputAction.GetProfile): Flow<VkCommand> {
        return getProfileByType(action.profileParams)
            .flatMapConcat { flowOf(ProfileOutputAction.SetProfile(it) as VkCommand) }
            .onStart { emit(ProfileOutputAction.ProfileLoading) }
            .retryDefault()
            .catch { emit(ProfileOutputAction.ProfileError(it.message.orEmpty())) }
    }

    private fun getProfileByType(profileParams: ProfileParams) = when (profileParams.type) {
        ProfileType.USER -> repository.getUserProfile(userId = profileParams.id)
        ProfileType.GROUP -> repository.getGroupProfile(groupId = profileParams.id)
    }
}