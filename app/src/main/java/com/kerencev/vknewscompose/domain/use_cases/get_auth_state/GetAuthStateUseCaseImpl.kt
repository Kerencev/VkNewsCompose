package com.kerencev.vknewscompose.domain.use_cases.get_auth_state

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : GetAuthStateUseCase {

    override operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }

}