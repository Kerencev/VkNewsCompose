package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetPhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class ProfileFeatureModule {

    @Provides
    fun provideGetWallFeature(profileRepository: ProfileRepository): GetWallFeature {
        return GetWallFeatureImpl(profileRepository)
    }

    @Provides
    fun provideGetProfileFeature(profileRepository: ProfileRepository): GetProfileFeature {
        return GetProfileFeatureImpl(profileRepository)
    }

    @Provides
    fun provideGetPhotosFeature(profileRepository: ProfileRepository): GetPhotosFeature {
        return GetPhotosFeatureImpl(profileRepository)
    }

}