package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.comments.CommentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel

}