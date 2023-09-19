package com.kerencev.vknewscompose.di.common

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.activity.MainViewModel
import com.kerencev.vknewscompose.presentation.screens.search.SearchViewModel
import com.kerencev.vknewscompose.presentation.screens.suggested.SuggestedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SuggestedViewModel::class)
    @Binds
    fun bindSuggestedViewModel(viewModel: SuggestedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

}