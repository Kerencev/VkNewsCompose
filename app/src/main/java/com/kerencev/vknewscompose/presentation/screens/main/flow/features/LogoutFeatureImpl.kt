package com.kerencev.vknewscompose.presentation.screens.main.flow.features

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainInputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainOutputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutFeatureImpl @Inject constructor(
    private val repository: AuthRepository
) : LogoutFeature {

    override fun invoke(p1: MainInputAction.Logout, p2: MainState): Flow<VkCommand> {
        return flow {
            repository.logout()
            emit(MainOutputAction.SetAuthState(AuthState.LOG_OUT))
        }
    }
}