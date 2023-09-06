package com.kerencev.vknewscompose.di.module.profile

import androidx.lifecycle.ViewModel
import com.kerencev.vknewscompose.di.annotation.ViewModelKey
import com.kerencev.vknewscompose.presentation.screens.profile_photos.ProfilePhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfilePhotosViewModelModule {

    @IntoMap
    @ViewModelKey(ProfilePhotosViewModel::class)
    @Binds
    fun bindProfilePhotosViewModel(viewModel: ProfilePhotosViewModel): ViewModel

}