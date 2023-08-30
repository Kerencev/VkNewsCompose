package com.kerencev.vknewscompose.data.utils

/**
 * Helper class for local data caching with pagination
 */
class PagingCache<T> {

    private val cache = mutableMapOf<Long, MutableList<T>>()
    private var page = mutableMapOf<Long, Int>()
    private var totalCount = mutableMapOf<Long, Int>()

    fun clearCacheById(id: Long) {
        cache[id]?.clear()
        page[id] = 0
        totalCount[id] = 0
    }

    fun isRemoteDataOver(id: Long): Boolean {
        val photos = getById(id)
        return photos.isNotEmpty() && photos.size >= getRemoteTotalCountById(id)
    }

    fun getById(id: Long): List<T> {
        return cache[id] ?: emptyList()
    }

    fun getRemoteTotalCountById(id: Long): Int {
        return totalCount[id] ?: 0
    }

    fun getCurrentPageById(id: Long): Int {
        return page[id] ?: 0
    }

    fun updateCacheById(
        id: Long,
        data: List<T>,
        remoteTotalCount: Int
    ): List<T> {
        page[id] = getCurrentPageById(id) + 1
        totalCount[id] = remoteTotalCount
        addToCacheById(id, data)
        return getById(id)
    }

    private fun addToCacheById(id: Long, data: List<T>) {
        if (cache[id] == null) cache[id] = mutableListOf()
        data.forEach { photo ->
            if (cache[id]?.contains(photo) == false) {
                cache[id]?.add(photo)
            }
        }
    }

}