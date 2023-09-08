package com.kerencev.vknewscompose.di.news

import com.kerencev.vknewscompose.di.common.ViewModelFactory
import com.kerencev.vknewscompose.presentation.screens.news.NewsParams
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        NewsViewModelModule::class,
        NewsFeatureModule::class
    ]
)
interface NewsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance params: NewsParams): NewsScreenComponent
    }
}