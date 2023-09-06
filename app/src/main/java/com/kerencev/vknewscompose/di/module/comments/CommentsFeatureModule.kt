package com.kerencev.vknewscompose.di.module.comments

import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.kerencev.vknewscompose.presentation.screens.comments.flow.features.GetCommentsFeature
import com.kerencev.vknewscompose.presentation.screens.comments.flow.features.GetCommentsFeatureImpl
import dagger.Module
import dagger.Provides

@Module
class CommentsFeatureModule {

    @Provides
    fun provideGetCommentsFeature(commentsRepository: CommentsRepository): GetCommentsFeature {
        return GetCommentsFeatureImpl(commentsRepository)
    }

}