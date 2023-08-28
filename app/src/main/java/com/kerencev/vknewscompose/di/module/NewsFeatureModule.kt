package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.ChangeLikeStatusFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.features.GetNewsFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class NewsFeatureModule {

    @Provides
    fun provideGetNewsFeature(newsFeedRepository: NewsFeedRepository): GetNewsFeature {
        return GetNewsFeatureImpl(newsFeedRepository)
    }

    @Provides
    fun provideChangeLikeStatusFeature(newsFeedRepository: NewsFeedRepository): ChangeLikeStatusFeature {
        return ChangeLikeStatusFeatureImpl(newsFeedRepository)
    }

}