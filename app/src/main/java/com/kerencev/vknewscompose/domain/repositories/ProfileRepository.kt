package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * Get profile data
     */
    fun getProfile(): Flow<ProfileModel>

    /**
     * Get all profile photos
     */
    fun getProfilePhotos(): Flow<List<PhotoModel>>

    /**
     * Get wall posts
     * @param page - number of page
     * @param pageSize - size of page
     */
    fun getWallData(page: Int, pageSize: Int): Flow<WallModel>

}