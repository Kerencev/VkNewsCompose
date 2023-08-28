package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.module.NewsFeatureModule
import com.kerencev.vknewscompose.di.module.NewsViewModelModule
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