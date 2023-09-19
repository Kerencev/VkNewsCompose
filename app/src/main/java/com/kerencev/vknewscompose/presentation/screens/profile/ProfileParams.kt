package com.kerencev.vknewscompose.presentation.screens.profile

import com.kerencev.vknewscompose.domain.entities.ProfileType

data class ProfileParams(
    val id: Long,
    val type: ProfileType
)
