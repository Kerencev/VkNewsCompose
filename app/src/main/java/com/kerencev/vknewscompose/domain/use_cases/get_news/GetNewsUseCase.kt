package com.kerencev.vknewscompose.domain.use_cases.get_news

import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import kotlinx.coroutines.flow.Flow


interface GetNewsUseCase {

    operator fun invoke(): Flow<VkCommand>

}