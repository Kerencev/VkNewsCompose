package com.kerencev.vknewscompose.di.search

import com.kerencev.vknewscompose.presentation.screens.search.flow.features.SearchFeature
import com.kerencev.vknewscompose.presentation.screens.search.flow.features.SearchFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface SearchFeatureModule {

    @Binds
    fun bindSearchFeature(impl: SearchFeatureImpl): SearchFeature

}