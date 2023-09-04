package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.data.utils.PagingCache
import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : SearchRepository {

    companion object {
        private const val TAG = "SearchRepository"
        private const val PAGE_SIZE = 30
        private const val USER_ID = 0L
    }

    private val profileCache = PagingCache<Profile>()
    private var isRemoteDataOver = false

    override fun search(
        query: String,
        isRefresh: Boolean,
    ): Flow<PagingModel<Profile>> = flow {
        if (query.isEmpty()) {
            emit(PagingModel(isItemsOver = false, data = emptyList()))
            return@flow
        }
        if (isRefresh) {
            profileCache.clearCacheById(USER_ID)
            isRemoteDataOver = false
        }
        if (isRemoteDataOver) {
            emit(PagingModel(isItemsOver = true, data = profileCache.getById(USER_ID)))
        }
        val offset = profileCache.getCurrentPageById(USER_ID) * PAGE_SIZE
        val response = apiService.search(
            query = query,
            count = PAGE_SIZE,
            offset = offset,
        )
        val remoteData =
            response.response?.items
                ?.filter { it.type != null }
                ?.map { it.mapToModel() } ?: emptyList()
        val remoteCount = (response.response?.count ?: 0)
        val remoteTotalCount = offset + remoteCount
        isRemoteDataOver = remoteCount < PAGE_SIZE
        val currentData = profileCache.updateCacheById(
            id = USER_ID,
            data = remoteData,
            remoteTotalCount = remoteTotalCount
        )
        emit(
            PagingModel(
                isItemsOver = isRemoteDataOver,
                data = currentData
            )
        )
    }

}
