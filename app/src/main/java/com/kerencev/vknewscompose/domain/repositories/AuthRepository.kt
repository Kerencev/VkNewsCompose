package com.kerencev.vknewscompose.domain.repositories

import com.kerencev.vknewscompose.domain.entities.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun checkAuthState(): Flow<AuthState>

    fun logout()

}