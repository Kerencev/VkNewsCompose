package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.PhotosModel
import com.kerencev.vknewscompose.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * Get user profile data
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getUserProfile(userId: Long, isRefresh: Boolean = false): Flow<Profile>

    /**
     * Get group profile data
     * @param groupId - Id of the group for which the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getGroupProfile(groupId: Long, isRefresh: Boolean = false): Flow<Profile>

    /**
     * Get profile photos
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getProfilePhotos(userId: Long, isRefresh: Boolean = false): Flow<PhotosModel>

    /**
     * Get wall posts
     * @param userId - Id of the user for whom the data will be received
     * @param isRefresh - Updating data or getting it from the cache
     */
    fun getWallData(userId: Long, isRefresh: Boolean = false): Flow<PagingModel<NewsModel>>

    /**
     * Get a list of photos for the wall post
     * @param userId - Id of the user for whom the data will be received
     * @param itemId - Id of the wall post
     */
    fun getWallItemPhotos(userId: Long, itemId: Long): Flow<List<PhotoModel>>

}