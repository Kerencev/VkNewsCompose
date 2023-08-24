package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.friends.FriendsResponseDto
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.FriendModel
import com.kerencev.vknewscompose.domain.entities.FriendsModel
import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileViewModel
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

    private val friendsCache = mutableMapOf<Long, MutableList<FriendModel>>()
    private var page = mutableMapOf<Long, Int>()
    private var friendsTotalCount = mutableMapOf<Long, Int>()

    override fun getFriends(
        userId: Long,
        searchText: String,
        isRefresh: Boolean,
    ): Flow<FriendsModel> = flow {
        if (isRefresh) clearFriendsCacheById(userId)
        if (isFriendsOver(userId)) {
            emit(FriendsModel(friends = getFriendsById(userId), isFriendsOver = true))
            return@flow
        }
        val response = apiService.getFriends(
            token = getAccessToken(),
            userId = getCorrectUserId(userId),
            searchText = searchText,
            offset = getFriendsPageById(userId) * PAGE_SIZE,
            count = PAGE_SIZE,
        )
        val friends = updateFriendsCacheById(userId, response)
        emit(
            FriendsModel(
                friends = friends,
                isFriendsOver = friends.size >= getFriendsTotalCountById(userId)
            )
        )
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: error("Token is null")
    }

    private fun clearFriendsCacheById(userId: Long) {
        friendsCache[userId]?.clear()
        page[userId] = 0
        friendsTotalCount[userId] = 0
    }

    private fun isFriendsOver(userId: Long): Boolean {
        val friendsById = getFriendsById(userId)
        return friendsById.isNotEmpty() && friendsById.size >= getFriendsTotalCountById(userId)
    }

    private fun updateFriendsCacheById(
        userId: Long,
        response: FriendsResponseDto
    ): List<FriendModel> {
        page[userId] = getFriendsPageById(userId) + 1
        friendsTotalCount[userId] = response.response?.count ?: 0
        val friends = response.response?.items?.map { it.mapToModel() } ?: emptyList()
        addFriendsToCacheById(userId, friends)
        return getFriendsById(userId)
    }

    private fun getCorrectUserId(userId: Long): String {
        return if (userId == ProfileViewModel.DEFAULT_USER_ID) token?.userId.toString()
        else userId.toString()
    }

    private fun getFriendsById(userId: Long): List<FriendModel> {
        return friendsCache[userId] ?: emptyList()
    }

    private fun getFriendsTotalCountById(userId: Long): Int {
        return friendsTotalCount[userId] ?: 0
    }

    private fun getFriendsPageById(userId: Long): Int {
        return page[userId] ?: 0
    }

    private fun addFriendsToCacheById(userId: Long, items: List<FriendModel>) {
        if (friendsCache[userId] == null) friendsCache[userId] = mutableListOf()
        friendsCache[userId]?.addAll(items)
    }

}