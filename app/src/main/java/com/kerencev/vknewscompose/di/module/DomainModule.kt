package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.domain.repositories.CommentsRepository
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.kerencev.vknewscompose.domain.repositories.ProfileRepository
import com.kerencev.vknewscompose.domain.use_cases.change_auth_state.CheckAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.change_auth_state.CheckAuthStateUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.change_like_status.ChangeLikeStatusUseCase
import com.kerencev.vknewscompose.domain.use_cases.change_like_status.ChangeLikeStatusUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.delete_news.DeleteNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.delete_news.DeleteNewsUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.get_auth_state.GetAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_auth_state.GetAuthStateUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.get_comments.GetCommentsUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_comments.GetCommentsUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.get_news.GetNewsUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_news.GetNewsUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.get_wall.GetWallUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_wall.GetWallUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfilePhotosUseCase
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfilePhotosUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfileUseCase
import com.kerencev.vknewscompose.domain.use_cases.profile.GetProfileUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideCheckAuthStateUseCase(authRepository: AuthRepository): CheckAuthStateUseCase {
        return CheckAuthStateUseCaseImpl(authRepository)
    }

    @Provides
    fun provideChangeLikeStatusUseCase(newsFeedRepository: NewsFeedRepository): ChangeLikeStatusUseCase {
        return ChangeLikeStatusUseCaseImpl(newsFeedRepository)
    }

    @Provides
    fun provideDeleteNewsUseCase(newsFeedRepository: NewsFeedRepository): DeleteNewsUseCase {
        return DeleteNewsUseCaseImpl(newsFeedRepository)
    }

    @Provides
    fun provideGetAuthStateUseCase(authRepository: AuthRepository): GetAuthStateUseCase {
        return GetAuthStateUseCaseImpl(authRepository)
    }

    @Provides
    fun provideGetCommentsUseCase(commentsRepository: CommentsRepository): GetCommentsUseCase {
        return GetCommentsUseCaseImpl(commentsRepository)
    }

    @Provides
    fun provideGetNewsUseCase(newsFeedRepository: NewsFeedRepository): GetNewsUseCase {
        return GetNewsUseCaseImpl(newsFeedRepository)
    }

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideGetProfilePhotosUseCase(profileRepository: ProfileRepository): GetProfilePhotosUseCase {
        return GetProfilePhotosUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideGetWallUseCase(profileRepository: ProfileRepository): GetWallUseCase {
        return GetWallUseCaseImpl(profileRepository)
    }

}