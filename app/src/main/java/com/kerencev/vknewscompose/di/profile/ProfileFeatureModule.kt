package com.kerencev.vknewscompose.di.profile

import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.CalculateProfileParamsFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetAllProfileDataFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfileFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetWallFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface ProfileFeatureModule {

    @Binds
    fun bindGetWallFeature(impl: GetWallFeatureImpl): GetWallFeature

    @Binds
    fun bindGetProfileFeature(impl: GetProfileFeatureImpl): GetProfileFeature

    @Binds
    fun bindCalculateProfileParamsFeature(impl: CalculateProfileParamsFeatureImpl)
            : CalculateProfileParamsFeature

    @Binds
    fun bindGetAllProfileDataFeature(impl: GetAllProfileDataFeatureImpl): GetAllProfileDataFeature

}