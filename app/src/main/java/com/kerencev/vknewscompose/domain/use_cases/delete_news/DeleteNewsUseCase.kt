package com.kerencev.vknewscompose.domain.use_cases.delete_news

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import kotlinx.coroutines.flow.Flow

interface DeleteNewsUseCase {

    operator fun invoke(newsModel: NewsModel): Flow<VkCommand>

}