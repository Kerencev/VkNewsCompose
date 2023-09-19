package com.kerencev.vknewscompose.presentation.screens.main.flow

import androidx.compose.runtime.Stable
import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.common.mvi.VkState

@Stable
data class MainState(
    val authState: AuthState = AuthState.INITIAL
) : VkState {

    fun setAuthState(authState: AuthState) = copy(
        authState = authState
    )
}
