package com.kerencev.vknewscompose.data.repository

import android.util.Log
import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val storage: VKKeyValueStorage
) : AuthRepository {

    companion object {
        private const val TOKEN_TAG = "TOKEN_TAG"
        private var userId = 0L
        val currentUserId
            get() = userId
    }

    private val token
        get() = VKAccessToken.restore(storage)

    override fun checkAuthState() = flow {
        Log.d(TOKEN_TAG, token?.accessToken.toString())
        val currentToken = token
        val loggedIn = currentToken != null && currentToken.isValid
        userId = token?.userId?.value ?: 0
        emit(if (loggedIn) AuthState.AUTHORIZED else AuthState.NOT_AUTHORIZED)
    }

    override fun logout() {
        VKAccessToken.remove(storage)
    }

}