package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.FriendsRepository
import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeature
import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class FriendsFeatureModule {

    @Provides
    fun provideGetFriendsFeature(friendsRepository: FriendsRepository): GetFriendsFeature {
        return GetFriendsFeatureImpl(friendsRepository)
    }
}