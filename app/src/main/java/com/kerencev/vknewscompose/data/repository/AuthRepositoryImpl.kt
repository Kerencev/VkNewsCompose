package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val storage: VKKeyValueStorage
) : AuthRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    override fun checkAuthState() = flow {
        val currentToken = token
        val loggedIn = currentToken != null && currentToken.isValid
        emit(if (loggedIn) AuthState.AUTHORIZED else AuthState.NOT_AUTHORIZED)
    }

    override fun logout() {
        VKAccessToken.remove(storage)
    }

}