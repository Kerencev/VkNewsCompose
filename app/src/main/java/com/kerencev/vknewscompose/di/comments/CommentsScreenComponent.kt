package com.kerencev.vknewscompose.di.comments

import com.kerencev.vknewscompose.di.common.ViewModelFactory
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class,
        CommentsFeatureModule::class,
    ]
)
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance newsModel: NewsModelUi): CommentsScreenComponent
    }
}