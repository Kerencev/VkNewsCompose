package com.kerencev.vknewscompose.di.module.profile

import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeatureImpl
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
    fun provideCalculateProfileParamsFeature(): CalculateProfileParamsFeature {
        return CalculateProfileParamsFeatureImpl()
    }

    @Provides
    fun provideGetAllProfileDataFeature(profileRepository: ProfileRepository): GetAllProfileDataFeature {
        return GetAllProfileDataFeatureImpl(profileRepository)
    }

}