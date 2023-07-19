package com.kerencev.vknewscompose.presentation.activity

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainEffect
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainEvent
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainInputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainOutputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainShot
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.LogoutFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val checkAuthStateFeature: CheckAuthStateFeature,
    private val logoutFeature: LogoutFeature,
) : BaseViewModel<MainEvent, MainState, MainShot>() {

    init {
        send(MainEvent.CheckAuthState)
    }

    override fun initState() = MainState()

    override fun produceCommand(event: MainEvent): VkCommand {
        return when (event) {
            is MainEvent.CheckAuthState -> MainInputAction.CheckAuthState
            is MainEvent.ShowErrorMessage -> MainEffect.ShowErrorMessage(event.message)
            is MainEvent.OnSnackBarDismiss -> MainEffect.None
            is MainEvent.Logout -> MainInputAction.Logout
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is MainInputAction.CheckAuthState -> checkAuthStateFeature(action, state())
            is MainInputAction.Logout -> logoutFeature(action, state())
            else -> null
        }
    }

    override suspend fun produceShot(effect: VkEffect) {
        when (effect) {
            is MainEffect.ShowErrorMessage -> setShot { MainShot.ShowErrorMessage(effect.message) }
            is MainEffect.None -> setShot { MainShot.None }
        }
    }

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is MainOutputAction.SetAuthState -> setState { setAuthState(action.result) }
        }
    }

}