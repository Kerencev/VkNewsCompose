package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.module.FriendsFeatureModule
import com.kerencev.vknewscompose.di.module.FriendsViewModelModule
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        FriendsViewModelModule::class,
        FriendsFeatureModule::class
    ]
)
interface FriendsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance userId: Long): FriendsScreenComponent
    }
}