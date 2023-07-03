package com.kerencev.vknewscompose.di.component

import android.content.Context
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.di.module.DataModule
import com.kerencev.vknewscompose.di.module.DomainModule
import com.kerencev.vknewscompose.di.module.FeatureModule
import com.kerencev.vknewscompose.di.module.PresentationModule
import com.kerencev.vknewscompose.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        DomainModule::class,
        PresentationModule::class,
        FeatureModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}