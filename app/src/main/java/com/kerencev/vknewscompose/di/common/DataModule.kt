package com.kerencev.vknewscompose.di.common

import com.kerencev.vknewscompose.data.repository.AuthRepositoryImpl
import com.kerencev.vknewscompose.data.repository.CommentsRepositoryImpl
import com.kerencev.vknewscompose.data.repository.FriendsRepositoryImpl
import com.kerencev.vknewscompose.data.repository.NewsFeedRepositoryImpl
import com.kerencev.vknewscompose.data.repository.ProfileRepositoryImpl
import com.kerencev.vknewscompose.data.repository.SearchRepositoryImpl
import com.kerencev.vknewscompose.data.repository.SuggestedRepositoryImpl
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.domain.repositories.SearchRepository
import com.kerencev.vknewscompose.domain.repositories.SuggestedRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

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
    fun bindFriendsRepository(impl: FriendsRepositoryImpl): FriendsRepository

    @ApplicationScope
    @Binds
    fun bindSuggestedRepository(impl: SuggestedRepositoryImpl): SuggestedRepository

    @ApplicationScope
    @Binds
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

}