package com.kerencev.vknewscompose.di.profile

import com.kerencev.vknewscompose.di.common.ViewModelFactory
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