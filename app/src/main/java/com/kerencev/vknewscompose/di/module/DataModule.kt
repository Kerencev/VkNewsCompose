package com.kerencev.vknewscompose.di.module

import android.content.Context
import com.kerencev.vknewscompose.data.api.ApiFactory
import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.comments.CommentsMapper
import com.kerencev.vknewscompose.data.mapper.comments.CommentsMapperImpl
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapper
import com.kerencev.vknewscompose.data.mapper.news_feed.NewsFeedMapperImpl
import com.kerencev.vknewscompose.data.mapper.profile.ProfileMapper
import com.kerencev.vknewscompose.data.mapper.profile.ProfileMapperImpl
import com.kerencev.vknewscompose.data.repository.AuthRepositoryImpl
import com.kerencev.vknewscompose.data.repository.CommentsRepositoryImpl
import com.kerencev.vknewscompose.data.repository.NewsFeedRepositoryImpl
import com.kerencev.vknewscompose.data.repository.ProfileRepositoryImpl
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(context: Context): VKKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }

    @ApplicationScope
    @Binds
    fun bindNewsFeedRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @ApplicationScope
    @Binds
    fun bindCommentsRepository(impl: CommentsRepositoryImpl): CommentsRepository

    @ApplicationScope
    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @ApplicationScope
    @Binds
    fun bindCommentsMapper(impl: CommentsMapperImpl): CommentsMapper

    @ApplicationScope
    @Binds
    fun bindNewsFeedMapper(impl: NewsFeedMapperImpl): NewsFeedMapper

    @ApplicationScope
    @Binds
    fun bindProfileMapper(impl: ProfileMapperImpl): ProfileMapper

}