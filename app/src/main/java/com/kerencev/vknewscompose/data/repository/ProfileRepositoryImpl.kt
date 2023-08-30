package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosResponseDto
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.data.utils.PagingCache
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.absoluteValue

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ProfileRepository {

    companion object {
        private const val PROFILE_NOT_FOUND = "Profile not found"
        private const val WALL_PAGE_SIZE = 5
        const val PHOTOS_PAGE_SIZE = 20
    }

    private val userProfileCache = mutableMapOf<Long, UserProfileModel?>()
    private val groupProfileCache = mutableMapOf<Long, GroupProfileModel?>()
    private val photosCacheQ = PagingCache<PhotoModel>()
//    private val photosCache = mutableMapOf<Long, MutableList<PhotoModel>>()
//    private var photosPage = mutableMapOf<Long, Int>()
//    private var photosTotalCount = mutableMapOf<Long, Int>()
    private val wallItemsCache = mutableMapOf<Long, MutableList<NewsModel>>()
    private var wallPage = mutableMapOf<Long, Int>()
    private var wallPostsTotalCount = mutableMapOf<Long, Int>()

    override fun getUserProfile(userId: Long, isRefresh: Boolean) = flow {
        if (userProfileCache[userId] == null || isRefresh) {
            val profileResponse = apiService.getProfile(usersIds = userId.toString())
                .response?.firstOrNull() ?: throw IllegalStateException(PROFILE_NOT_FOUND)
            val profileModel = profileResponse.mapToModel()
            userProfileCache[userId] = profileModel
            emit(profileModel)
        } else {
            delay(100)
            userProfileCache[userId]?.let { emit(it) }
        }
    }

    override fun getGroupProfile(groupId: Long, isRefresh: Boolean) = flow {
        if (groupProfileCache[groupId] == null || isRefresh) {
            val profileResponse =
                apiService.getGroupProfile(groupId = groupId.absoluteValue.toString())
                    .response?.firstOrNull() ?: throw IllegalStateException(PROFILE_NOT_FOUND)
            val profileModel = profileResponse.mapToModel()
            groupProfileCache[groupId] = profileModel
            emit(profileModel)
        } else {
            delay(100)
            groupProfileCache[groupId]?.let { emit(it) }
        }
    }

    override fun getProfilePhotos(userId: Long, isRefresh: Boolean) = flow {
//        if (isRefresh) clearPhotosCacheById(userId)
        if (isRefresh) photosCacheQ.clearCacheById(userId)
        if (photosCacheQ.isRemoteDataOver(userId)) {
            delay(100)
            emit(
                PhotosModel(
                    totalCount = photosCacheQ.getRemoteTotalCountById(userId),
                    photos = photosCacheQ.getById(userId)
                )
            )
            return@flow
        }
        val response = apiService.getProfilePhotos(
            ownerId = userId.toString(),
            offset = photosCacheQ.getCurrentPageById(userId) * PHOTOS_PAGE_SIZE,
            count = PHOTOS_PAGE_SIZE
        )
        val photos = photosCacheQ.updateCacheById(userId, response)
        emit(
            PhotosModel(
                totalCount = photosCacheQ.getRemoteTotalCountById(userId),
                photos = photos
            )
        )
    }

    override fun getWallData(userId: Long, isRefresh: Boolean) = flow {
        if (isRefresh) clearWallCacheById(userId)
        if (isWallItemsOver(userId)) {
            delay(100)
            emit(WallModel(items = getWallItemsById(userId), isItemsOver = true))
            return@flow
        }
        val response = apiService.getWall(
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
        items.forEach { item ->
            if (wallItemsCache[userId]?.contains(item) == false) {
                wallItemsCache[userId]?.add(item)
            }
        }
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

//    private fun clearPhotosCacheById(userId: Long) {
//        photosCacheT[userId]?.clear()
//        photosPage[userId] = 0
//        photosTotalCount[userId] = 0
//    }
//
//    private fun isPhotosOver(userId: Long): Boolean {
//        val photos = photosCacheQ.getById(userId)
//        return photos.isNotEmpty() && photos.size >= photosCacheQ.getRemoteTotalCountById(userId)
//    }
//
//    private fun getPhotosById(userId: Long): List<PhotoModel> {
//        return photosCacheT[userId] ?: emptyList()
//    }
//
//    private fun getPhotosTotalCountById(userId: Long): Int {
//        return photosTotalCount[userId] ?: 0
//    }
//
//    private fun getPhotosPageById(userId: Long): Int {
//        return photosPage[userId] ?: 0
//    }
//
//    private fun updatePhotosCacheById(
//        userId: Long,
//        response: ProfilePhotosResponseDto
//    ): List<PhotoModel> {
//        photosPage[userId] = photosCacheQ.getCurrentPageById(userId) + 1
//        photosTotalCount[userId] = response.response?.count ?: 0
//        val photos = response.response?.mapToModel() ?: emptyList()
//        addPhotosToCacheById(userId, photos)
//        return photosCacheQ.getById(userId)
//    }
//
//    private fun addPhotosToCacheById(userId: Long, photos: List<PhotoModel>) {
//        if (photosCacheT[userId] == null) photosCacheT[userId] = mutableListOf()
//        photos.forEach { photo ->
//            if (photosCacheT[userId]?.contains(photo) == false) {
//                photosCacheT[userId]?.add(photo)
//            }
//        }
//    }

}