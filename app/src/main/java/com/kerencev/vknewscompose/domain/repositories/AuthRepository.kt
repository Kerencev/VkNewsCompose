package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun checkAuthState()
}