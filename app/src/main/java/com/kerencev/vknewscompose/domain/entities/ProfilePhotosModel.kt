package com.kerencev.vknewscompose.domain.entities

data class PhotoModel(
    val id: Long,
    val date: Long?,
    val lat: Double?,
    val long: Double?,
    val url: String,
    val text: String,
    val likes: Int,
    val reposts: Int
)
