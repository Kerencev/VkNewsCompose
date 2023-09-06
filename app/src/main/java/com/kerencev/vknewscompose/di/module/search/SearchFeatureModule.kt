package com.kerencev.vknewscompose.di.module.search

import com.kerencev.vknewscompose.domain.repositories.SearchRepository
import com.kerencev.vknewscompose.presentation.screens.search.flow.features.SearchFeature
import com.kerencev.vknewscompose.presentation.screens.search.flow.features.SearchFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class SearchFeatureModule {

    @Provides
    fun provideSearchFeature(repository: SearchRepository): SearchFeature {
        return SearchFeatureImpl(repository)
    }

}