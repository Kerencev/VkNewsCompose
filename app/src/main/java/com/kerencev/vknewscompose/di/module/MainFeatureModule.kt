package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class MainFeatureModule {

    @Provides
    fun provideCheckAuthStateFeature(repository: AuthRepository): CheckAuthStateFeature {
        return CheckAuthStateFeatureImpl(repository)
    }

}