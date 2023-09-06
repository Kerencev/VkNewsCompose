package com.kerencev.vknewscompose.di.module.suggested

import com.kerencev.vknewscompose.domain.repositories.SuggestedRepository
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.features.GetSuggestedFeature
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.features.GetSuggestedFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class SuggestedFeatureModule {

    @Provides
    fun provideGetSuggestedFeature(repository: SuggestedRepository): GetSuggestedFeature {
        return GetSuggestedFeatureImpl(repository)
    }

}