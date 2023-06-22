package com.kerencev.vknewscompose.presentation.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.data.repository.AuthRepositoryImpl
import com.kerencev.vknewscompose.domain.repositories.AuthRepository
import com.kerencev.vknewscompose.domain.use_cases.CheckAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.GetAuthStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch(Dispatchers.IO) {
            checkAuthStateUseCase()
        }
    }

}