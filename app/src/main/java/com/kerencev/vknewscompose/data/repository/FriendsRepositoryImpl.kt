package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.FriendModel
import com.kerencev.vknewscompose.domain.entities.FriendsModel
import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
) : FriendsRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val token
        get() = VKAccessToken.restore(storage)

    private val friendsCache = mutableListOf<FriendModel>()
    private var page = 0
    private var friendsTotalCount = 0

    override fun getFriends(searchText: String, isRefresh: Boolean): Flow<FriendsModel> =
        flow {
            if (isRefresh) {
                friendsCache.clear()
                page = 0
                friendsTotalCount = 0
            }
            if (friendsCache.isNotEmpty() && friendsCache.size >= friendsTotalCount) {
                emit(FriendsModel(friends = friendsCache, isFriendsOver = true))
                return@flow
            }
            val response = apiService.getFriends(
                token = getAccessToken(),
                searchText = searchText,
                offset = page * PAGE_SIZE,
                count = PAGE_SIZE,
            )
            page++
            friendsTotalCount = response.response?.count ?: 0
            val friends = response.response?.items?.map { it.mapToModel() } ?: emptyList()
            friendsCache.addAll(friends)
            emit(
                FriendsModel(
                    friends = friendsCache,
                    isFriendsOver = friendsCache.size >= friendsTotalCount
                )
            )
        }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: error("Token is null")
    }
}