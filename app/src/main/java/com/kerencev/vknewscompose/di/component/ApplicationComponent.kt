package com.kerencev.vknewscompose.di.component

import android.content.Context
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.di.module.DataModule
import com.kerencev.vknewscompose.di.module.HomeFeatureModule
import com.kerencev.vknewscompose.di.module.MainFeatureModule
import com.kerencev.vknewscompose.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        MainFeatureModule::class,
        HomeFeatureModule::class,
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    fun getProfileScreenComponentFactory(): ProfileScreenComponent.Factory

    fun getFriendsScreenComponentFactory(): FriendsScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}