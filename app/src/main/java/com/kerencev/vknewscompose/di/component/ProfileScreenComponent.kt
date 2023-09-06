package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.module.profile.ProfileFeatureModule
import com.kerencev.vknewscompose.di.module.profile.ProfileViewModelModule
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfileViewModelModule::class,
        ProfileFeatureModule::class,
    ]
)
interface ProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance profileParams: ProfileParams): ProfileScreenComponent
    }
}