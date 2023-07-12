package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalculateProfileParamsFeatureImpl @Inject constructor() : CalculateProfileParamsFeature {

    private var firstVisibleItem = 0
    private var firstVisibleItemScrollOffset = 0

    override fun invoke(
        action: ProfileInputAction.CalculateUiParams,
        state: ProfileViewState
    ): Flow<VkCommand> = flow {
        action.firstVisibleItem?.let { firstVisibleItem = it }
        action.firstVisibleItemScrollOffset?.let { firstVisibleItemScrollOffset = it }
        val topBarAlpha =
            if (firstVisibleItem == 0) (firstVisibleItemScrollOffset / 300f).coerceAtMost(1f)
            else 1f
        val blurBackgroundAlpha = 1f - (topBarAlpha + 0.5f)
        val avatarAlpha = 1f - topBarAlpha
        val avatarSize = ((1f - topBarAlpha) * 100f)
        emit(
            ProfileOutputAction.SetUiParams(
                topBarAlpha = topBarAlpha,
                blurBackgroundAlpha = blurBackgroundAlpha,
                avatarAlpha = avatarAlpha,
                avatarSize = avatarSize
            )
        )
    }
}