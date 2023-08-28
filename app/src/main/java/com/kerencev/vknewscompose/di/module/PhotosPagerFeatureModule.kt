package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetPostPhotosFeatureImpl
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeature
import com.kerencev.vknewscompose.presentation.screens.photos_pager.flow.fetures.GetWallPostPhotosFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class PhotosPagerFeatureModule {

    @Provides
    fun provideGetWallPostPhotosFeature(profileRepository: ProfileRepository): GetWallPostPhotosFeature {
        return GetWallPostPhotosFeatureImpl(profileRepository)
    }

    @Provides
    fun provideGetPostPhotosFeature(newsFeedRepository: NewsFeedRepository): GetPostPhotosFeature {
        return GetPostPhotosFeatureImpl(newsFeedRepository)
    }
}