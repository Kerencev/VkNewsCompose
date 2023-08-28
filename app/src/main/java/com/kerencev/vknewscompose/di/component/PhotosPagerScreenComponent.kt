package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.module.PhotosPagerFeatureModule
import com.kerencev.vknewscompose.di.module.PhotosPagerViewModelModule
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerParams
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        PhotosPagerViewModelModule::class,
        PhotosPagerFeatureModule::class,
    ]
)
interface PhotosPagerScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance params: PhotosPagerParams,
        ): PhotosPagerScreenComponent
    }
}