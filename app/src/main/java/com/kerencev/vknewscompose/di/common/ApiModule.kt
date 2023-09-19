package com.kerencev.vknewscompose.di.common

import android.content.Context
import com.kerencev.vknewscompose.data.api.ApiFactory
import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.api.auth.AuthInterceptor
import com.kerencev.vknewscompose.data.api.auth.ReLoginExecutor
import com.kerencev.vknewscompose.data.api.auth.ReLoginExecutorImpl
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module
interface ApiModule {

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(authInterceptor: Interceptor): ApiService {
            return ApiFactory(authInterceptor).apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(context: Context): VKKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }

    }

    @ApplicationScope
    @Binds
    fun bindAuthInterceptor(impl: AuthInterceptor): Interceptor

    @ApplicationScope
    @Binds
    fun bindReLoginExecutor(impl: ReLoginExecutorImpl): ReLoginExecutor

}