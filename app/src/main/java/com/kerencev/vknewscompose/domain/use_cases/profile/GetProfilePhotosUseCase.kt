package com.kerencev.vknewscompose.domain.use_cases.profile

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfilePhotosModel
import kotlinx.coroutines.flow.Flow

interface GetProfilePhotosUseCase {

    operator fun invoke(): Flow<DataResult<List<PhotoModel>>>
}