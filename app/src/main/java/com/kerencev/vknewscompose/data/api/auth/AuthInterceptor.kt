package com.kerencev.vknewscompose.data.api.auth

import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val storage: VKKeyValueStorage,
    private val reLoginExecutor: ReLoginExecutor,
) : Interceptor {

    companion object {
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header(AUTHORIZATION, "$BEARER ${getAccessToken()}")
            .build()
        return chain.proceed(modifiedRequest)
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String? {
        val token = VKAccessToken.restore(storage)?.accessToken
        if (token == null) reLoginExecutor.reLogin()
        return token
    }
}