package com.kerencev.vknewscompose.data.api.auth

import android.util.Log
import com.vk.api.sdk.VKTokenExpiredHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReLoginExecutorImpl @Inject constructor() : ReLoginExecutor {

    companion object {
        private const val TAG = "ReLoginExecutorImpl"
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private val reLoginState = MutableSharedFlow<Unit>()

    init {
        object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                Log.d(TAG, "VKTokenExpiredHandler: onTokenExpired")
                reLogin()
            }
        }
    }

    override fun reLogin() {
        scope.launch {
            reLoginState.emit(Unit)
        }
    }

    override fun observeReLogin(): Flow<Unit> {
        return reLoginState.asSharedFlow()
    }

}