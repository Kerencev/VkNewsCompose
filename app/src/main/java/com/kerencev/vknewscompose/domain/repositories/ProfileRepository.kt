package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * Get profile data
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getProfile(isRefresh: Boolean = false): Flow<ProfileModel>

    /**
     * Get all profile photos
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getProfilePhotos(isRefresh: Boolean = false): Flow<List<PhotoModel>>

    /**
     * Get wall posts
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getWallData(isRefresh: Boolean = false): Flow<WallModel>

    fun getWallItemPhotos(itemId: Long): Flow<List<PhotoModel>>

}