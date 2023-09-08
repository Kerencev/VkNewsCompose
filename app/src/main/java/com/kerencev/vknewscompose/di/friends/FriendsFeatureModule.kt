package com.kerencev.vknewscompose.di.friends

import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeature
import com.kerencev.vknewscompose.presentation.screens.friends.flow.features.GetFriendsFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface FriendsFeatureModule {

    @Binds
    fun bindGetFriendsFeature(impl: GetFriendsFeatureImpl): GetFriendsFeature

}