package com.kerencev.vknewscompose.presentation.screens.profile.flow.features

import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileInputAction
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileOutputAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalculateProfileParamsFeatureImpl @Inject constructor() : CalculateProfileParamsFeature {

    private var firstVisibleItem = 0
    private var firstVisibleItemScrollOffset = 0

    override fun invoke(action: ProfileInputAction.CalculateUiParams): Flow<VkCommand> = flow {
        action.firstVisibleItem?.let { firstVisibleItem = it }
        action.firstVisibleItemScrollOffset?.let { firstVisibleItemScrollOffset = it }

        val topBarAlpha = getTopBarAlpha()
        val blurBackgroundAlpha = getBlurBackgroundAlpha(action.profileType, topBarAlpha)

        emit(
            ProfileOutputAction.SetUiParams(
                topBarAlpha = topBarAlpha,
                blurBackgroundAlpha = blurBackgroundAlpha,
                avatarAlpha = 1f - topBarAlpha,
                avatarSize = ((1f - topBarAlpha) * 100f)
            )
        )
    }

    private fun getTopBarAlpha() =
        if (firstVisibleItem == 0) (firstVisibleItemScrollOffset / 300f).coerceAtMost(1f)
        else 1f

    private fun getBlurBackgroundAlpha(profileType: ProfileType, topBarAlpha: Float) =
        when (profileType) {
            ProfileType.USER -> 1f - (topBarAlpha + 0.5f)
            ProfileType.GROUP -> 1f - (topBarAlpha)
        }
}
