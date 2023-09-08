package com.kerencev.vknewscompose.di.photos_pager

import com.kerencev.vknewscompose.di.common.ViewModelFactory
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