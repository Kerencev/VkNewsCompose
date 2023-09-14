package com.kerencev.vknewscompose.di.common

import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface CommonFeatureModule {

    @Binds
    fun bindGetProfilePhotosFeature(impl: GetProfilePhotosFeatureImpl): GetProfilePhotosFeature

    @Binds
    fun bindChangeLikeStatusFeature(impl: ChangeLikeStatusFeatureImpl): ChangeLikeStatusFeature

}