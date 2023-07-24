package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetWallFeatureImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetWallFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: ProfileInputAction.GetWall,
        state: ProfileViewState
    ): Flow<VkCommand> {
        return repository.getWallData()
            .flatMapConcat { result ->
                flowOf(
                    ProfileOutputAction.SetWall(
                        wallItems = result.items,
                        isItemsOver = result.isItemsOver
                    ) as VkCommand
                )
            }
            .onStart { emit(ProfileOutputAction.WallLoading) }
            .retryDefault()
            .catch { emit(ProfileOutputAction.WallError(it.message.orEmpty())) }
    }
}