package com.kerencev.vknewscompose.di.news

import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface NewsFeatureModule {

    @Binds
    fun bindGetNewsFeature(impl: GetNewsFeatureImpl): GetNewsFeature

}