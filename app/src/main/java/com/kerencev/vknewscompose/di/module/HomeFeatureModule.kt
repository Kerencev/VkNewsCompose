package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.ChangeLikeStatusFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeature
import com.kerencev.vknewscompose.presentation.screens.home.flow.features.GetNewsFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeature
import com.kerencev.vknewscompose.presentation.screens.profile.flow.features.GetProfilePhotosFeatureImpl
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

    @Provides
    fun provideGetProfilePhotosFeature(profileRepository: ProfileRepository): GetProfilePhotosFeature {
        return GetProfilePhotosFeatureImpl(profileRepository)
    }

    @Provides
    fun provideGetWallPostPhotosFeature(profileRepository: ProfileRepository): GetWallPostPhotosFeature {
        return GetWallPostPhotosFeatureImpl(profileRepository)
    }
}