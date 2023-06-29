package com.kerencev.vknewscompose.domain.use_cases.get_wall

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetWallUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetWallUseCase {

    override operator fun invoke(page: Int) = repository.getWallData(page)

}