package com.kerencev.vknewscompose.di.component

import com.kerencev.vknewscompose.di.module.CommentsViewModelModule
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance newsModel: NewsModelUi): CommentsScreenComponent
    }
}