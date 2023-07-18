package com.kerencev.vknewscompose.presentation.screens.main.flow.features

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainInputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainOutputAction
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CheckAuthStateFeatureImpl @Inject constructor(
    private val repository: AuthRepository
) : CheckAuthStateFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: MainInputAction.CheckAuthState,
        state: MainState
    ): Flow<VkCommand> {
        return repository.checkAuthState()
            .flatMapConcat { flowOf(MainOutputAction.SetAuthState(it)) }
            .catch { emit(MainOutputAction.SetAuthState(AuthState.NOT_AUTHORIZED)) }
    }

}