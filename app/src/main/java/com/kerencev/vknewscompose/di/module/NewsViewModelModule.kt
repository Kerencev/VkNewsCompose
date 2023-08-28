package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsViewModelModule {

    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    @Binds
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel

}