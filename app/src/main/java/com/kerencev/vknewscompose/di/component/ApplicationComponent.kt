package com.kerencev.vknewscompose.di.component

import android.content.Context
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.di.module.app.CommonFeatureModule
import com.kerencev.vknewscompose.di.module.app.MainFeatureModule
import com.kerencev.vknewscompose.di.module.search.SearchFeatureModule
import com.kerencev.vknewscompose.di.module.suggested.SuggestedFeatureModule
import com.kerencev.vknewscompose.di.module.app.DataModule
import com.kerencev.vknewscompose.di.module.app.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        MainFeatureModule::class,
        SuggestedFeatureModule::class,
        SearchFeatureModule::class,
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