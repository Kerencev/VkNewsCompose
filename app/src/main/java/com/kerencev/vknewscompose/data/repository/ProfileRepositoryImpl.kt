package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.dto.profile.ProfileDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosDto
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
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

    private var profileCache: ProfileDto? = null
    private var profilePhotosCache: ProfilePhotosDto? = null
    private val wallItemsCache = mutableListOf<NewsModel>()
    private var wallPage = 0
    private var wallPostsTotalCount = 0

    override fun getProfile(isRefresh: Boolean) = flow {
        if (profileCache == null || isRefresh) {
            val profileResponse = apiService.getProfile(
                token = getAccessToken(),
                usersIds = token?.userId.toString()
            ).response?.firstOrNull() ?: throw IllegalStateException(PROFILE_NOT_FOUND)
            profileCache = profileResponse
            emit(profileResponse)
        } else profileCache?.let { emit(it) }
    }
        .map { it.mapToModel() }

    override fun getProfilePhotos(isRefresh: Boolean) = flow {
        if (profilePhotosCache == null || isRefresh) {
            val photosResponse = apiService.getProfilePhotos(
                token = getAccessToken(),
                ownerId = token?.userId.toString()
            ).response ?: throw IllegalStateException(PROFILE_PHOTOS_NOT_FOUND)
            profilePhotosCache = photosResponse
            emit(photosResponse)
        } else profilePhotosCache?.let { emit(it) }
    }
        .map { it.mapToModel() }

    override fun getWallData(isRefresh: Boolean) = flow {
        if (isRefresh) {
            wallItemsCache.clear()
            wallPage = 0
            wallPostsTotalCount = 0
        }

        if (wallItemsCache.isNotEmpty() && wallItemsCache.size >= wallPostsTotalCount) {
            emit(WallModel(items = wallItemsCache, isItemsOver = true))
            return@flow
        }

        val response = apiService.getWall(
            token = getAccessToken(),
            offset = wallPage * WALL_PAGE_SIZE,
            count = WALL_PAGE_SIZE
        )
        wallPage++
        wallPostsTotalCount = response.response?.count ?: 0
        wallItemsCache.addAll(response.mapToModel())
        emit(
            WallModel(
                items = wallItemsCache,
                isItemsOver = wallItemsCache.size >= wallPostsTotalCount
            )
        )
    }

    override fun getWallItemPhotos(itemId: Long): Flow<List<PhotoModel>> = flow {
        val post = wallItemsCache.firstOrNull { it.id == itemId }
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

}