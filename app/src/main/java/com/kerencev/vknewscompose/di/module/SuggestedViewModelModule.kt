package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.suggested.SuggestedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SuggestedViewModelModule {

    @IntoMap
    @ViewModelKey(SuggestedViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: SuggestedViewModel): ViewModel

}