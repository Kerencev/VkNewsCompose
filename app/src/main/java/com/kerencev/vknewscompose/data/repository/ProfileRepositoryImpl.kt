package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosResponseDto
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
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
        private const val TOKEN_IS_NULL = "Token is null"
        private const val WALL_PAGE_SIZE = 5
        private const val PHOTOS_PAGE_SIZE = 20
    }

    private val token
        get() = VKAccessToken.restore(storage)

    private val profileCache = mutableMapOf<Long, ProfileDto?>()
    private val photosCache = mutableMapOf<Long, MutableList<PhotoModel>>()
    private var photosPage = mutableMapOf<Long, Int>()
    private var photosTotalCount = mutableMapOf<Long, Int>()
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
        if (isRefresh) clearPhotosCacheById(userId)
        if (isPhotosOver(userId)) {
            emit(
                PhotosModel(
                    totalCount = getPhotosTotalCountById(userId),
                    photos = getPhotosById(userId)
                )
            )
            return@flow
        }
        val response = apiService.getProfilePhotos(
            token = getAccessToken(),
            ownerId = getCorrectUserId(userId),
            offset = getPhotosPageById(userId) * PHOTOS_PAGE_SIZE,
            count = PHOTOS_PAGE_SIZE
        )
        val photos = updatePhotosCacheById(userId, response)
        emit(
            PhotosModel(
                totalCount = getPhotosTotalCountById(userId),
                photos = photos
            )
        )
    }

    override fun getWallData(userId: Long, isRefresh: Boolean) = flow {
        if (isRefresh) clearWallCacheById(userId)
        if (isWallItemsOver(userId)) {
            emit(WallModel(items = getWallItemsById(userId), isItemsOver = true))
            return@flow
        }
        val response = apiService.getWall(
            token = getAccessToken(),
            userId = getCorrectUserId(userId),
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

    override fun getWallItemPhotos(userId: Long, itemId: Long): Flow<List<PhotoModel>> = flow {
        val post = wallItemsCache[userId]?.firstOrNull { it.id == itemId }
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

    private fun clearPhotosCacheById(userId: Long) {
        photosCache[userId]?.clear()
        photosPage[userId] = 0
        photosTotalCount[userId] = 0
    }

    private fun isPhotosOver(userId: Long): Boolean {
        val photos = getPhotosById(userId)
        return photos.isNotEmpty() && photos.size >= getPhotosTotalCountById(userId)
    }

    private fun getPhotosById(userId: Long): List<PhotoModel> {
        return photosCache[userId] ?: emptyList()
    }

    private fun getPhotosTotalCountById(userId: Long): Int {
        return photosTotalCount[userId] ?: 0
    }

    private fun getPhotosPageById(userId: Long): Int {
        return photosPage[userId] ?: 0
    }

    private fun updatePhotosCacheById(
        userId: Long,
        response: ProfilePhotosResponseDto
    ): List<PhotoModel> {
        photosPage[userId] = getPhotosPageById(userId) + 1
        photosTotalCount[userId] = response.response?.count ?: 0
        val photos = response.response?.mapToModel() ?: emptyList()
        addPhotosToCacheById(userId, photos)
        return getPhotosById(userId)
    }

    private fun addPhotosToCacheById(userId: Long, photos: List<PhotoModel>) {
        if (photosCache[userId] == null) photosCache[userId] = mutableListOf()
        photosCache[userId]?.addAll(photos)
    }

}