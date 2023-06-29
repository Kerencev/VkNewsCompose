package com.kerencev.vknewscompose.domain.use_cases.profile

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository

class GetProfilePhotosUseCaseImpl(
    private val profileRepository: ProfileRepository
) : GetProfilePhotosUseCase {

    override fun invoke() = profileRepository.getProfilePhotos()
}