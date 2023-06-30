package com.kerencev.vknewscompose.domain.use_cases.change_like_status

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import kotlinx.coroutines.flow.Flow

interface ChangeLikeStatusUseCase {

    operator fun invoke(newsModel: NewsModel): Flow<VkCommand>

}