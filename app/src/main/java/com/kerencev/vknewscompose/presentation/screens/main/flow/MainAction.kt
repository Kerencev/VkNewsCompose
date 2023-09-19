package com.kerencev.vknewscompose.presentation.screens.main.flow

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect

sealed class MainInputAction : VkAction {

    /**
     * Get state of user's authorization
     */
    object CheckAuthState : MainInputAction()

    object Logout : MainInputAction()

}

sealed class MainOutputAction : VkAction {

    /**
     * Set state of user's authorization
     */
    class SetAuthState(val result: AuthState) : MainOutputAction()

}

sealed class MainEffect : VkEffect {

    class ShowSnackBar(val message: String) : MainEffect()

    object None : MainEffect()

}