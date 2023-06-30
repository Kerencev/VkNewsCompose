package com.kerencev.vknewscompose.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.vknewscompose.domain.use_cases.change_auth_state.CheckAuthStateUseCase
import com.kerencev.vknewscompose.domain.use_cases.get_auth_state.GetAuthStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch(Dispatchers.IO) {
            checkAuthStateUseCase()
        }
    }

}