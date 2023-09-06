package com.kerencev.vknewscompose.di.module.news

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.news.NewsViewModel
import com.kerencev.vknewscompose.presentation.screens.news.RecommendationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsViewModelModule {

    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    @Binds
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RecommendationViewModel::class)
    @Binds
    fun bindRecommendationViewModel(viewModel: RecommendationViewModel): ViewModel

}