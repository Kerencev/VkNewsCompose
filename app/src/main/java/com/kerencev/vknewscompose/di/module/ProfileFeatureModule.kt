package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeatureImpl
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

    @Provides
    fun provideCalculateProfileParamsFeature(): CalculateProfileParamsFeature {
        return CalculateProfileParamsFeatureImpl()
    }

    @Provides
    fun provideGetAllProfileDataFeature(profileRepository: ProfileRepository): GetAllProfileDataFeature {
        return GetAllProfileDataFeatureImpl(profileRepository)
    }

}