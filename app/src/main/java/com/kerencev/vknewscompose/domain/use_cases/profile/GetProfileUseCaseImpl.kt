package com.kerencev.vknewscompose.domain.use_cases.profile

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : GetProfileUseCase {

    override fun invoke() = profileRepository.getProfile()
}