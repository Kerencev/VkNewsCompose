package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosDto
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileViewModel
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
) : ProfileRepository {

    companion object {
        private const val PROFILE_NOT_FOUND = "Profile not found"
        private const val PROFILE_PHOTOS_NOT_FOUND = "Profile photos not found"
        private const val TOKEN_IS_NULL = "Token is null"
        private const val WALL_PAGE_SIZE = 5
    }

    private val token
        get() = VKAccessToken.restore(storage)

    private val profileCache = mutableMapOf<Long, ProfileDto?>()
    private val profilePhotosCache = mutableMapOf<Long, ProfilePhotosDto?>()
    private val wallItemsCache = mutableMapOf<Long, MutableList<NewsModel>>()
    private var wallPage = mutableMapOf<Long, Int>()
    private var wallPostsTotalCount = mutableMapOf<Long, Int>()

    override fun getProfile(userId: Long, isRefresh: Boolean) = flow {
        if (profileCache[userId] == null || isRefresh) {
            val profileResponse = apiService.getProfile(
                token = getAccessToken(),
                usersIds = getCorrectUserId(userId)
            ).response?.firstOrNull() ?: throw IllegalStateException(PROFILE_NOT_FOUND)
            profileCache[userId] = profileResponse
            emit(profileResponse)
        } else profileCache[userId]?.let { emit(it) }
    }
        .map { it.mapToModel() }

    override fun getProfilePhotos(userId: Long, isRefresh: Boolean) = flow {
        if (profilePhotosCache[userId] == null || isRefresh) {
            val photosResponse = apiService.getProfilePhotos(
                token = getAccessToken(),
                ownerId = getCorrectUserId(userId)
            ).response ?: throw IllegalStateException(PROFILE_PHOTOS_NOT_FOUND)
            profilePhotosCache[userId] = photosResponse
            emit(photosResponse)
        } else profilePhotosCache[userId]?.let { emit(it) }
    }
        .map { it.mapToModel() }

    override fun getWallData(userId: Long, isRefresh: Boolean) = flow {
        if (isRefresh) clearWallCacheById(userId)
        if (isWallItemsOver(userId)) {
            emit(WallModel(items = getWallItemsById(userId), isItemsOver = true))
            return@flow
        }
        val response = apiService.getWall(
            token = getAccessToken(),
            userId = userId.toString(),
            offset = getWallPageById(userId) * WALL_PAGE_SIZE,
            count = WALL_PAGE_SIZE
        )
        val wallItems = updateWallCacheById(userId, response)
        emit(
            WallModel(
                items = wallItems,
                isItemsOver = wallItems.size >= getWallItemsTotalCountById(userId)
            )
        )
    }

    override fun getWallItemPhotos(itemId: Long): Flow<List<PhotoModel>> = flow {
        val post = wallItemsCache[0]?.firstOrNull { it.id == itemId }
        if (post == null) emit(emptyList())
        else {
            emit(
                post.imageContent.map { image ->
                    PhotoModel(
                        id = image.id,
                        date = null,
                        lat = null,
                        long = null,
                        url = image.url,
                        height = image.height,
                        width = image.width,
                        text = "",
                        likes = 0,
                        reposts = 0
                    )
                }
            )
        }
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken() = token?.accessToken ?: error(TOKEN_IS_NULL)

    private fun clearWallCacheById(userId: Long) {
        wallItemsCache[userId]?.clear()
        wallPage[userId] = 0
        wallPostsTotalCount[userId] = 0
    }

    private fun isWallItemsOver(userId: Long): Boolean {
        val wallItems = getWallItemsById(userId)
        return wallItems.isNotEmpty() && wallItems.size >= getWallItemsTotalCountById(userId)
    }

    private fun updateWallCacheById(
        userId: Long,
        response: NewsFeedResponseDto
    ): List<NewsModel> {
        wallPage[userId] = getWallPageById(userId) + 1
        wallPostsTotalCount[userId] = response.response?.count ?: 0
        val wallItems = response.mapToModel()
        addWallItemsToCacheById(userId, wallItems)
        return getWallItemsById(userId)
    }

    private fun addWallItemsToCacheById(userId: Long, items: List<NewsModel>) {
        if (wallItemsCache[userId] == null) wallItemsCache[userId] = mutableListOf()
        wallItemsCache[userId]?.addAll(items)
    }

    private fun getWallItemsById(userId: Long): List<NewsModel> {
        return wallItemsCache[userId] ?: emptyList()
    }

    private fun getWallItemsTotalCountById(userId: Long): Int {
        return wallPostsTotalCount[userId] ?: 0
    }

    private fun getWallPageById(userId: Long): Int {
        return wallPage[userId] ?: 0
    }

    private fun getCorrectUserId(userId: Long): String {
        return if (userId == ProfileViewModel.DEFAULT_USER_ID) token?.userId.toString()
        else userId.toString()
    }

}