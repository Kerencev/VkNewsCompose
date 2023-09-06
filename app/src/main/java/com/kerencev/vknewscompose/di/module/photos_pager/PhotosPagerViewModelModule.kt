package com.kerencev.vknewscompose.di.module.photos_pager

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PhotosPagerViewModelModule {

    @IntoMap
    @ViewModelKey(PhotosPagerViewModel::class)
    @Binds
    fun bindPhotosPagerViewModel(viewModel: PhotosPagerViewModel): ViewModel

}