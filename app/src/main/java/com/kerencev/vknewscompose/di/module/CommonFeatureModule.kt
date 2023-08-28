package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class CommonFeatureModule {

    @Provides
    fun provideGetProfilePhotosFeature(profileRepository: ProfileRepository): GetProfilePhotosFeature {
        return GetProfilePhotosFeatureImpl(profileRepository)
    }

}