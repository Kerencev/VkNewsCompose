package com.kerencev.vknewscompose.presentation.screens.comments.flow.features

import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsInputAction
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsOutputAction
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCommentsFeatureImpl @Inject constructor(
    private val repository: CommentsRepository
) : GetCommentsFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(
        action: CommentsInputAction.GetComments,
        state: CommentsViewState
    ): Flow<VkCommand> {
        return repository.getComments(action.newsModel)
            .flatMapConcat {
                flowOf(CommentsOutputAction.SetComments(it) as VkCommand)
            }
            .onStart { emit(CommentsOutputAction.Loading) }
            .retryDefault()
            .catch { emit(CommentsOutputAction.Error(it.message.orEmpty())) }
    }
}