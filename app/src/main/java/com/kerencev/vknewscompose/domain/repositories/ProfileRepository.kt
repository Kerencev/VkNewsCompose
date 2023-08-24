package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * Get profile data
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getProfile(userId: Long, isRefresh: Boolean = false): Flow<ProfileModel>

    /**
     * Get all profile photos
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getProfilePhotos(userId: Long, isRefresh: Boolean = false): Flow<List<PhotoModel>>

    /**
     * Get wall posts
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getWallData(userId: Long, isRefresh: Boolean = false): Flow<WallModel>

    /**
     * Get a list of photos for the wall post
     * @param itemId - Id of the wall post
     */
    fun getWallItemPhotos(itemId: Long): Flow<List<PhotoModel>>

}