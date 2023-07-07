package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
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
    fun provideGetProfilePhotosFeature(profileRepository: ProfileRepository): GetProfilePhotosFeature {
        return GetProfilePhotosFeatureImpl(profileRepository)
    }

}