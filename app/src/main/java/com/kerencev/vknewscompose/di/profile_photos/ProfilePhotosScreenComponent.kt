package com.kerencev.vknewscompose.di.profile_photos

import com.kerencev.vknewscompose.di.common.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfilePhotosViewModelModule::class,
    ]
)
interface ProfilePhotosScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance userId: Long): ProfilePhotosScreenComponent
    }
}