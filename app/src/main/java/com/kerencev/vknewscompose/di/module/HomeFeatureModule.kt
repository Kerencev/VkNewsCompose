package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class HomeFeatureModule {

    @Provides
    fun provideGetNewsFeature(newsFeedRepository: NewsFeedRepository): GetNewsFeature {
        return GetNewsFeatureImpl(newsFeedRepository)
    }

    @Provides
    fun provideChangeLikeStatusFeature(newsFeedRepository: NewsFeedRepository): ChangeLikeStatusFeature {
        return ChangeLikeStatusFeatureImpl(newsFeedRepository)
    }

    @Provides
    fun provideGetPostPhotosFeature(newsFeedRepository: NewsFeedRepository): GetPostPhotosFeature {
        return GetPostPhotosFeatureImpl(newsFeedRepository)
    }
}