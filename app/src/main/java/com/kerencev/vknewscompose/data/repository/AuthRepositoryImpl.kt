package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val storage: VKKeyValueStorage
) : AuthRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private val checkAuthState = MutableSharedFlow<Unit>(replay = 1)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val authState = flow {
        checkAuthState.emit(Unit)
        checkAuthState.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            emit(if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow() = authState

    override suspend fun checkAuthState() {
        checkAuthState.emit(Unit)
    }

}