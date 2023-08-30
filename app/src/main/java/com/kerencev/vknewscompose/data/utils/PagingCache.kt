package com.kerencev.vknewscompose.data.utils

import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosResponseDto
import com.kerencev.vknewscompose.data.mapper.mapToModel

class PagingCache<T> {

    private val cache = mutableMapOf<Long, MutableList<T>>()
    private var page = mutableMapOf<Long, Int>()
    private var totalCount = mutableMapOf<Long, Int>()

    fun clearCacheById(userId: Long) {
        cache[userId]?.clear()
        page[userId] = 0
        totalCount[userId] = 0
    }

    fun isRemoteDataOver(userId: Long): Boolean {
        val photos = getById(userId)
        return photos.isNotEmpty() && photos.size >= getRemoteTotalCountById(userId)
    }

    fun getById(userId: Long): List<T> {
        return cache[userId] ?: emptyList()
    }

    fun getRemoteTotalCountById(userId: Long): Int {
        return totalCount[userId] ?: 0
    }

    fun getCurrentPageById(userId: Long): Int {
        return page[userId] ?: 0
    }

    fun updateCacheById(
        userId: Long,
        response: ProfilePhotosResponseDto
    ): List<T> {
        page[userId] = getCurrentPageById(userId) + 1
        totalCount[userId] = response.response?.count ?: 0
        val photos = (response.response?.mapToModel() ?: emptyList()) as List<T>
        addToCacheById(userId, photos)
        return getById(userId)
    }

    private fun addToCacheById(userId: Long, photos: List<T>) {
        if (cache[userId] == null) cache[userId] = mutableListOf()
        photos.forEach { photo ->
            if (cache[userId]?.contains(photo) == false) {
                cache[userId]?.add(photo)
            }
        }
    }

}