package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.data.utils.PagingCache
import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : FriendsRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val friendsCache = PagingCache<UserProfileModel>()

    override fun getFriends(
        userId: Long,
        searchText: String,
        isRefresh: Boolean,
    ): Flow<PagingModel<UserProfileModel>> = flow {
        if (isRefresh) friendsCache.clearCacheById(userId)
        if (friendsCache.isRemoteDataOver(userId)) {
            emit(PagingModel(data = friendsCache.getById(userId), isItemsOver = true))
            return@flow
        }
        val response = apiService.getFriends(
            userId = userId.toString(),
            query = searchText,
            offset = friendsCache.getCurrentPageById(userId) * PAGE_SIZE,
            count = PAGE_SIZE,
        )
        val remoteFriends = response.response?.items?.map { it.mapToModel() } ?: emptyList()
        val remoteTotalCount = response.response?.count ?: 0
        val friends = friendsCache.updateCacheById(
            id = userId,
            data = remoteFriends,
            remoteTotalCount = remoteTotalCount
        )
        emit(
            PagingModel(
                data = friends,
                isItemsOver = friends.size >= remoteTotalCount
            )
        )
    }

}
