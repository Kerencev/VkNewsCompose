package com.kerencev.vknewscompose.di.module

import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.domain.use_cases.change_auth_state.CheckAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.change_auth_state.CheckAuthStateUseCaseImpl
import com.kerencev.vknewscompose.domain.use_cases.get_auth_state.GetAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_auth_state.GetAuthStateUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideCheckAuthStateUseCase(authRepository: AuthRepository): CheckAuthStateUseCase {
        return CheckAuthStateUseCaseImpl(authRepository)
    }

    @Provides
    fun provideGetAuthStateUseCase(authRepository: AuthRepository): GetAuthStateUseCase {
        return GetAuthStateUseCaseImpl(authRepository)
    }

}