package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.module.ProfileFeatureModule
import com.kerencev.vknewscompose.di.module.ProfileViewModelModule
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfileViewModelModule::class,
        ProfileFeatureModule::class
    ]
)
interface ProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance userId: Long): ProfileScreenComponent
    }
}