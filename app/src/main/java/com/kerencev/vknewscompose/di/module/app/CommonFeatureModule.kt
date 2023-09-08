package com.kerencev.vknewscompose.di.module.app

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
import dagger.Module
import dagger.Provides

//TODO: Заменить все Provides на Binds для своих классов

@Module
class CommonFeatureModule {

    @Provides
    fun provideGetProfilePhotosFeature(profileRepository: ProfileRepository): GetProfilePhotosFeature {
        return GetProfilePhotosFeatureImpl(profileRepository)
    }

}