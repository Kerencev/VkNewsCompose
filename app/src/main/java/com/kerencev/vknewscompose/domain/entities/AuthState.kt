package com.kerencev.vknewscompose.domain.entities

sealed class AuthState {

    object Initial : AuthState()

    object Authorized : AuthState()

    object NotAuthorized : AuthState()

}