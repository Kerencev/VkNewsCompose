package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface SuggestedRepository {

    fun getSuggested(): Flow<List<Profile>>
}