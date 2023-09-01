package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.repositories.SuggestedRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SuggestedRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SuggestedRepository {

    override fun getSuggested() = flow {
        val response = apiService.getSuggested(offset = 0, count = 100)
        val suggestedItems = response.response?.items?.map { it.mapToModel() }
        emit(suggestedItems ?: emptyList())
    }
}