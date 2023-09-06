package com.kerencev.vknewscompose.di.module.app

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.LogoutFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.LogoutFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class MainFeatureModule {

    @Provides
    fun provideCheckAuthStateFeature(repository: AuthRepository): CheckAuthStateFeature {
        return CheckAuthStateFeatureImpl(repository)
    }

    @Provides
    fun provideLogoutFeature(repository: AuthRepository): LogoutFeature {
        return LogoutFeatureImpl(repository)
    }

}