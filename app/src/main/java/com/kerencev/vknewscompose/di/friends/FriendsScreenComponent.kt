package com.kerencev.vknewscompose.di.friends

import com.kerencev.vknewscompose.di.common.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        FriendsViewModelModule::class,
        FriendsFeatureModule::class,
    ]
)
interface FriendsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance userId: Long): FriendsScreenComponent
    }
}