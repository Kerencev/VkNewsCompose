package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.data.utils.PagingCache
import com.kerencev.vknewscompose.domain.entities.FriendsModel
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
    ): Flow<FriendsModel> = flow {
        if (isRefresh) friendsCache.clearCacheById(userId)
        if (friendsCache.isRemoteDataOver(userId)) {
            emit(FriendsModel(friends = friendsCache.getById(userId), isFriendsOver = true))
            return@flow
        }
        val response = apiService.getFriends(
            userId = userId.toString(),
            searchText = searchText,
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
            FriendsModel(
                friends = friends,
                isFriendsOver = friends.size >= remoteTotalCount
            )
        )
    }

}
