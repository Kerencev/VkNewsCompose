package com.kerencev.vknewscompose.di.module

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.activity.MainViewModel
import com.kerencev.vknewscompose.presentation.screens.home.HomeViewModel
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerViewModel
import com.kerencev.vknewscompose.presentation.screens.profile_photos.ProfilePhotosViewModel
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
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PhotosPagerViewModel::class)
    @Binds
    fun bindProfilePhotosPagerViewModel(viewModel: PhotosPagerViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfilePhotosViewModel::class)
    @Binds
    fun bindProfilePhotosViewModel(viewModel: ProfilePhotosViewModel): ViewModel

}