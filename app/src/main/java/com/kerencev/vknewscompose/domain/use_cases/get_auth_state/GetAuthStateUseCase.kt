package com.kerencev.vknewscompose.domain.use_cases.get_auth_state

import com.kerencev.vknewscompose.domain.entities.AuthState
import kotlinx.coroutines.flow.StateFlow

interface GetAuthStateUseCase {

    operator fun invoke(): StateFlow<AuthState>
}