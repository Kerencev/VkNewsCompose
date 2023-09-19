package com.kerencev.vknewscompose.di.app

import android.content.Context
import com.kerencev.vknewscompose.di.annotation.ApplicationScope
import com.kerencev.vknewscompose.di.comments.CommentsScreenComponent
import com.kerencev.vknewscompose.di.common.ApiModule
import com.kerencev.vknewscompose.di.common.CommonFeatureModule
import com.kerencev.vknewscompose.di.common.DataModule
import com.kerencev.vknewscompose.di.common.ViewModelFactory
import com.kerencev.vknewscompose.di.common.ViewModelModule
import com.kerencev.vknewscompose.di.friends.FriendsScreenComponent
import com.kerencev.vknewscompose.di.main.MainFeatureModule
import com.kerencev.vknewscompose.di.news.NewsScreenComponent
import com.kerencev.vknewscompose.di.photos_pager.PhotosPagerScreenComponent
import com.kerencev.vknewscompose.di.profile.ProfileScreenComponent
import com.kerencev.vknewscompose.di.profile_photos.ProfilePhotosScreenComponent
import com.kerencev.vknewscompose.di.search.SearchFeatureModule
import com.kerencev.vknewscompose.di.suggested.SuggestedFeatureModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApiModule::class,
        DataModule::class,
        ViewModelModule::class,
        MainFeatureModule::class,
        SuggestedFeatureModule::class,
        SearchFeatureModule::class,
        CommonFeatureModule::class,
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getNewsScreenComponentFactory(): NewsScreenComponent.Factory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    fun getProfileScreenComponentFactory(): ProfileScreenComponent.Factory

    fun getProfilePhotosComponentFactory(): ProfilePhotosScreenComponent.Factory

    fun getFriendsScreenComponentFactory(): FriendsScreenComponent.Factory

    fun getPhotosPagerScreenComponentFactory(): PhotosPagerScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}