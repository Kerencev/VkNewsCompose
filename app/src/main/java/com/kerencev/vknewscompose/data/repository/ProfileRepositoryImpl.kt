package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.data.mapper.profile.ProfileMapper
import com.kerencev.vknewscompose.domain.entities.WallModel
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
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
        private const val RETRY_COUNT = 2L
        private const val RETRY_DELAY = 3_000L
        private const val WALL_PAGE_SIZE = 5
    }

    private val token
        get() = VKAccessToken.restore(storage)

    override fun getProfile() = flow {
        emit(DataResult.Loading)
        val response = apiService.getProfile(
            token = getAccessToken(),
            usersIds = token?.userId.toString()
        )
        if (response.response?.firstOrNull() == null) throw IllegalStateException(
            PROFILE_NOT_FOUND
        )
        else emit(DataResult.Success(profileMapper.mapToEntity(response.response.first())))
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }
        .flowOn(Dispatchers.IO)

    override fun getProfilePhotos() = flow {
        emit(DataResult.Loading)
        val response = apiService.getProfilePhotos(
            token = getAccessToken(),
            ownerId = token?.userId.toString()
        )
        if (response.response?.items == null || response.response.count == null) {
            throw IllegalStateException(PROFILE_PHOTOS_NOT_FOUND)
        } else {
            emit(DataResult.Success(profileMapper.mapToEntity(response.response)))
        }
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }
        .flowOn(Dispatchers.IO)

    override fun getWallData(page: Int) = flow {
        emit(DataResult.Loading)
        val response = apiService.getWall(
            token = getAccessToken(),
            offset = page * WALL_PAGE_SIZE,
            count = WALL_PAGE_SIZE
        )
        emit(
            DataResult.Success(
                WallModel(
                    totalCount = response.response?.count ?: 0,
                    items = newsMapper.mapToEntity(response)
                )
            )
        )
    }
        .retry(RETRY_COUNT) {
            delay(RETRY_DELAY)
            true
        }
        .catch { emit(DataResult.Error(it)) }
        .flowOn(Dispatchers.IO)

    @Throws(IllegalStateException::class)
    private fun getAccessToken() = token?.accessToken ?: error("Token is null")

}