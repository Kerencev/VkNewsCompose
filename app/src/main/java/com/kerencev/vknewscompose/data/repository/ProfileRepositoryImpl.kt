package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.data.mapper.profile.ProfileMapper
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
    private val profileMapper: ProfileMapper,
    private val newsMapper: NewsFeedMapper
) : ProfileRepository {

    companion object {
        private const val PROFILE_NOT_FOUND = "Profile not found"
        private const val PROFILE_PHOTOS_NOT_FOUND = "Profile photos not found"
    }

    private val token
        get() = VKAccessToken.restore(storage)

    private var profilePhotosCache: List<PhotoModel>? = null

    override fun getProfile() = flow {
        val response = apiService.getProfile(
            token = getAccessToken(),
            usersIds = token?.userId.toString()
        )
        if (response.response?.firstOrNull() == null)
            throw IllegalStateException(PROFILE_NOT_FOUND)
        emit(profileMapper.mapToEntity(response.response.first()))
    }

    override fun getProfilePhotos() = flow {
        val photos = profilePhotosCache ?: run {
            val response = apiService.getProfilePhotos(
                token = getAccessToken(),
                ownerId = token?.userId.toString()
            )

            if (response.response?.items == null || response.response.count == null)
                throw IllegalStateException(PROFILE_PHOTOS_NOT_FOUND)

            profileMapper.mapToEntity(response.response)
        }
        profilePhotosCache = photos
        emit(photos)
    }

    override fun getWallData(page: Int, pageSize: Int) = flow {
        val response = apiService.getWall(
            token = getAccessToken(),
            offset = page * pageSize,
            count = pageSize
        )
        emit(
            WallModel(
                totalCount = response.response?.count ?: 0,
                items = newsMapper.mapToEntity(response)
            )
        )
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken() = token?.accessToken ?: error("Token is null")

}