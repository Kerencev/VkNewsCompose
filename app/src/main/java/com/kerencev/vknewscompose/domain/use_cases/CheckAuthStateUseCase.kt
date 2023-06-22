package com.kerencev.vknewscompose.domain.use_cases

import com.kerencev.vknewscompose.domain.repositories.AuthRepository

class CheckAuthStateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        return repository.checkAuthState()
    }

}