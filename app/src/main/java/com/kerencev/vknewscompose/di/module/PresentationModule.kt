package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapperImpl
import dagger.Binds
import dagger.Module

@Module
interface PresentationModule {

    @ApplicationScope
    @Binds
    fun bindNewsModelMapper(impl: NewsModelMapperImpl): NewsModelMapper
}