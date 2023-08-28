package com.kerencev.vknewscompose.di.component

import android.content.Context
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.di.module.CommonFeatureModule
import com.kerencev.vknewscompose.di.module.DataModule
import com.kerencev.vknewscompose.di.module.MainFeatureModule
import com.kerencev.vknewscompose.di.module.MainViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        MainViewModelModule::class,
        MainFeatureModule::class,
        CommonFeatureModule::class,
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getNewsScreenComponentFactory(): NewsScreenComponent.Factory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    fun getProfileScreenComponentFactory(): ProfileScreenComponent.Factory

    fun getProfilePhotosComponentFactory(): ProfilePhotosScreenComponent.Factory

    fun getFriendsScreenComponentFactory(): FriendsScreenComponent.Factory

    fun getPhotosPagerScreenComponentFactory(): PhotosPagerScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}