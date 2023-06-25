package com.kerencev.vknewscompose.domain.use_cases.change_auth_state

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import javax.inject.Inject

class CheckAuthStateUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : CheckAuthStateUseCase {

    override suspend operator fun invoke() {
        return repository.checkAuthState()
    }

}