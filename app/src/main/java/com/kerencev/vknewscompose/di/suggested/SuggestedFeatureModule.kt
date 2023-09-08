package com.kerencev.vknewscompose.di.suggested

import com.kerencev.vknewscompose.presentation.screens.suggested.flow.features.GetSuggestedFeature
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.features.GetSuggestedFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface SuggestedFeatureModule {

    @Binds
    fun bindGetSuggestedFeature(impl: GetSuggestedFeatureImpl): GetSuggestedFeature

}