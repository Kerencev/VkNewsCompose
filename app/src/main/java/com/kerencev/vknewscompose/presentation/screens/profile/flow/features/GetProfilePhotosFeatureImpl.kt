package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkState
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetProfilePhotosFeatureImpl @Inject constructor(
    private val repository: ProfileRepository,
) : GetProfilePhotosFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: VkAction,
        state: VkState,
    ): Flow<VkCommand> {
        return repository.getProfilePhotos()
            .flatMapConcat {
                flowOf(ProfileOutputAction.SetProfilePhotos(it) as VkCommand)
            }
            .onStart { emit(ProfileOutputAction.ProfilePhotosLoading) }
            .retryDefault()
            .catch { emit(ProfileOutputAction.ProfilePhotosError(it.message.orEmpty())) }
    }
}