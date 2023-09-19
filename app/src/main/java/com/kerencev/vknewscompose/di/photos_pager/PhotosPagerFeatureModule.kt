package com.kerencev.vknewscompose.di.photos_pager

import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface PhotosPagerFeatureModule {

    @Binds
    fun bindGetWallPostPhotosFeature(impl: GetWallPostPhotosFeatureImpl): GetWallPostPhotosFeature

    @Binds
    fun bindGetPostPhotosFeature(impl: GetPostPhotosFeatureImpl): GetPostPhotosFeature
}