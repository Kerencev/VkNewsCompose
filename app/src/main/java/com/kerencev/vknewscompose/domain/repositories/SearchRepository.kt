package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.PagingModel
import com.kerencev.vknewscompose.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    /**
     * Get list of users and communities
     * @param query - The text by which the list of users and communities will be received
     * @param isRefresh - The list of users and communities is updated or added
     */
    fun search(query: String, isRefresh: Boolean): Flow<PagingModel<Profile>>
}