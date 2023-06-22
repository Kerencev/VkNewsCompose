package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }

}