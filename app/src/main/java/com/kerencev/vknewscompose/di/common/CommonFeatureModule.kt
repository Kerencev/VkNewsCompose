package com.kerencev.vknewscompose.di.common

import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface CommonFeatureModule {

    @Binds
    fun bindGetProfilePhotosFeature(impl: GetProfilePhotosFeatureImpl): GetProfilePhotosFeature

}