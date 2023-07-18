package com.kerencev.vknewscompose.presentation.screens.main.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent

sealed class MainEvent : VkEvent {

    /**
     * Get state of user's authorization
     */
    object CheckAuthState : MainEvent()

    class ShowErrorMessage(val message: String) : MainEvent()

    /**
     * When SnackBar is dismissed
     */
    object OnSnackBarDismiss : MainEvent()

}