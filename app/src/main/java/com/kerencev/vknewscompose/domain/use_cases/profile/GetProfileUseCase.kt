package com.kerencev.vknewscompose.domain.use_cases.profile

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import kotlinx.coroutines.flow.Flow

interface GetProfileUseCase {

    operator fun invoke(): Flow<DataResult<ProfileModel>>
}