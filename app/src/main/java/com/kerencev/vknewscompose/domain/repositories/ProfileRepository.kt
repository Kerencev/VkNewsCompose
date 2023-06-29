package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.common.DataResult
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.entities.ProfileModel
import com.kerencev.vknewscompose.domain.entities.WallModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<DataResult<ProfileModel>>

    fun getProfilePhotos(): Flow<DataResult<List<PhotoModel>>>

    fun getWallData(page: Int): Flow<DataResult<WallModel>>

}