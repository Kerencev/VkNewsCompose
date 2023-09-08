package com.kerencev.vknewscompose.data.api.auth

import kotlinx.coroutines.flow.Flow

interface ReLoginExecutor {

    fun reLogin()

    fun observeReLogin(): Flow<Unit>

}