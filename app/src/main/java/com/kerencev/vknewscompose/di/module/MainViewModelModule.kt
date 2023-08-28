package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.activity.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}