package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface SuggestedRepository {

    /**
     * Get list of users, groups and communities recommended for the user
     */
    fun getSuggested(): Flow<List<Profile>>
}
