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

        val topBarAlpha = getTopBarAlpha(action.profileType)
        val blurBackgroundAlpha = getBlurBackgroundAlpha(action.profileType, topBarAlpha)
        val avatarAlpha = getAvatarAlpha(topBarAlpha)
        val avatarSize = getAvatarSize(topBarAlpha)

        emit(
            ProfileOutputAction.SetUiParams(
                topBarAlpha = topBarAlpha,
                blurBackgroundAlpha = blurBackgroundAlpha,
                avatarAlpha = avatarAlpha,
                avatarSize = avatarSize
            )
        )
    }

    private fun getTopBarAlpha(profileType: ProfileType) =
        if (firstVisibleItem == 0) {
            (firstVisibleItemScrollOffset / getAlphaDivider(profileType))
                .coerceAtMost(1f)
        } else 1f

    private fun getAlphaDivider(profileType: ProfileType) = when (profileType) {
        ProfileType.USER -> 300f
        ProfileType.GROUP -> 100f
    }

    private fun getBlurBackgroundAlpha(profileType: ProfileType, topBarAlpha: Float) =
        when (profileType) {
            ProfileType.USER -> 1f - (topBarAlpha + 0.5f)
            ProfileType.GROUP -> 1f - (topBarAlpha)
        }

    private fun getAvatarAlpha(topBarAlpha: Float) = 1f - topBarAlpha

    private fun getAvatarSize(topBarAlpha: Float) = ((1f - topBarAlpha) * 100f)
}