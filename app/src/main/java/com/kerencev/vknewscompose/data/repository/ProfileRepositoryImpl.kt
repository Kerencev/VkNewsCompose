package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.data.utils.PagingCache
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.domain.mapper.toPhotoModel
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
    private val photosCache = PagingCache<PhotoModel>()
    private val wallItemsCache = PagingCache<NewsModel>()

    override fun getUserProfile(userId: Long, isRefresh: Boolean) = flow {
        if (userProfileCache[userId] == null || isRefresh) {
            val profileResponse = apiService.getUserProfile(usersIds = userId.toString())
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
        if (isRefresh) photosCache.clearCacheById(userId)
        if (photosCache.isRemoteDataOver(userId)) {
            delay(100)
            emit(
                PhotosModel(
                    totalCount = photosCache.getRemoteTotalCountById(userId),
                    photos = photosCache.getById(userId)
                )
            )
            return@flow
        }
        val response = apiService.getProfilePhotos(
            ownerId = userId.toString(),
            offset = photosCache.getCurrentPageById(userId) * PHOTOS_PAGE_SIZE,
            count = PHOTOS_PAGE_SIZE
        )
        val remoteTotalCount = response.response?.count ?: 0
        val remotePhotos = response.response?.mapToModel() ?: emptyList()
        val photos = photosCache.updateCacheById(
            id = userId,
            data = remotePhotos,
            remoteTotalCount = remoteTotalCount
        )
        emit(
            PhotosModel(
                totalCount = photosCache.getRemoteTotalCountById(userId),
                photos = photos
            )
        )
    }

    override fun getWallData(userId: Long, isRefresh: Boolean) = flow {
        if (isRefresh) wallItemsCache.clearCacheById(userId)
        if (wallItemsCache.isRemoteDataOver(userId)) {
            delay(100)
            emit(PagingModel(data = wallItemsCache.getById(userId), isItemsOver = true))
            return@flow
        }
        val response = apiService.getWall(
            userId = userId.toString(),
            offset = wallItemsCache.getCurrentPageById(userId) * WALL_PAGE_SIZE,
            count = WALL_PAGE_SIZE
        )
        val remoteTotalCount = response.response?.count ?: 0
        val remoteItems = response.mapToModel()
        val wallItems = wallItemsCache.updateCacheById(
            id = userId,
            data = remoteItems,
            remoteTotalCount = remoteTotalCount
        )
        emit(
            PagingModel(
                data = wallItems,
                isItemsOver = wallItems.size >= remoteTotalCount
            )
        )
    }

    override fun getWallItemPhotos(userId: Long, itemId: Long): Flow<List<PhotoModel>> = flow {
        val post = wallItemsCache.getById(userId).firstOrNull { it.id == itemId }
        emit(post?.imageContent?.map { it.toPhotoModel() } ?: emptyList())
    }

}