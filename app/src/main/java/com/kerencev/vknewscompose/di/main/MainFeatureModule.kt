package com.kerencev.vknewscompose.di.main

import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.CheckAuthStateFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.LogoutFeature
import com.kerencev.vknewscompose.presentation.screens.main.flow.features.LogoutFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface MainFeatureModule {

    @Binds
    fun bindCheckAuthStateFeature(impl: CheckAuthStateFeatureImpl): CheckAuthStateFeature

    @Binds
    fun bindLogoutFeature(impl: LogoutFeatureImpl): LogoutFeature

}