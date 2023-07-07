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

    companion object {
        private const val WALL_PAGE_SIZE = 5
    }

    private var wallPage = 0
    private var wallPostsTotalCount = 0

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: ProfileInputAction.GetWall,
        state: ProfileViewState
    ): Flow<VkCommand> {
        if (wallPage != 0 && (wallPage * WALL_PAGE_SIZE) >= wallPostsTotalCount)
            return flowOf(ProfileOutputAction.WallItemsIsOver)

        return repository.getWallData(wallPage, WALL_PAGE_SIZE)
            .flatMapConcat {
                wallPage++
                wallPostsTotalCount = it.totalCount
                flowOf(ProfileOutputAction.SetWall(it) as VkCommand)
            }
            .onStart { emit(ProfileOutputAction.WallLoading) }
            .retryDefault()
            .catch { emit(ProfileOutputAction.WallError(it.message.orEmpty())) }
    }
}