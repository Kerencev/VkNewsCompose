package com.kerencev.vknewscompose.presentation.screens.search.flow.features

import com.kerencev.vknewscompose.domain.repositories.SearchRepository
import com.kerencev.vknewscompose.extensions.retryDefault
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchInputAction
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchOutputAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchFeatureImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchFeature {

    @OptIn(FlowPreview::class)
    override fun invoke(action: SearchInputAction.GetData): Flow<VkCommand> {
        return repository.search(
            query = action.query,
            isRefresh = action.isRefresh
        )
            .flatMapConcat { profiles ->
                flowOf(
                    SearchOutputAction.SetData(
                        query = action.query,
                        data = profiles.data,
                        isItemsOver = profiles.isItemsOver
                    ) as SearchOutputAction
                )
            }
            .onStart {
                emit(
                    SearchOutputAction.Loading(isRefresh = action.isRefresh, query = action.query)
                )
            }
            .retryDefault()
            .catch {
                emit(
                    SearchOutputAction.Error(message = it.message.orEmpty(), query = action.query)
                )
            }
    }
}