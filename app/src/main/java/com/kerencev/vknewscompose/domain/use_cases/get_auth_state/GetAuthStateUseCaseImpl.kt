package com.kerencev.vknewscompose.domain.use_cases.get_auth_state

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import javax.inject.Inject

class GetAuthStateUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : GetAuthStateUseCase {

    override operator fun invoke() = repository.getAuthStateFlow()

}