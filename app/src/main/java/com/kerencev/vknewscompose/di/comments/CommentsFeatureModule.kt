package com.kerencev.vknewscompose.di.comments

import com.kerencev.vknewscompose.presentation.screens.comments.flow.features.GetCommentsFeature
import com.kerencev.vknewscompose.presentation.screens.comments.flow.features.GetCommentsFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface CommentsFeatureModule {

    @Binds
    fun bindGetCommentsFeature(impl: GetCommentsFeatureImpl): GetCommentsFeature

}