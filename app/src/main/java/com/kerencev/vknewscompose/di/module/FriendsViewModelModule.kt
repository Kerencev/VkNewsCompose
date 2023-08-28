package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.friends.FriendsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FriendsViewModelModule {

    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    @Binds
    fun bindFriendsViewModel(viewModel: FriendsViewModel): ViewModel
}