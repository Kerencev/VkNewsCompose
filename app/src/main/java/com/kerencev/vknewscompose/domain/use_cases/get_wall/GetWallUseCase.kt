package com.kerencev.vknewscompose.domain.use_cases.get_wall

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.WallModel
import kotlinx.coroutines.flow.Flow

interface GetWallUseCase {

    operator fun invoke(page: Int): Flow<DataResult<WallModel>>
}