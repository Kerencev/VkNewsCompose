package com.kerencev.vknewscompose.presentation.screens.suggested.flow.features

import com.kerencev.vknewscompose.domain.repositories.SuggestedRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedInputAction
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedOutputAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetSuggestedFeatureImpl @Inject constructor(
    private val repository: SuggestedRepository
) : GetSuggestedFeature {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(action: SuggestedInputAction.GetData): Flow<VkCommand> {
        return repository.getSuggested()
            .flatMapConcat { flowOf(SuggestedOutputAction.SetData(it) as VkCommand) }
            .onStart { emit(SuggestedOutputAction.Loading(isRefreshing = action.isRefreshing)) }
            .retryDefault()
            .catch { emit(SuggestedOutputAction.Error(it.message.orEmpty())) }
    }
}