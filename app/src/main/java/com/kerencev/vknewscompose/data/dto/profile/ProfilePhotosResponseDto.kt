package com.kerencev.vknewscompose.data.dto.profile

import com.kerencev.vknewscompose.data.dto.news_feed.Likes
import com.kerencev.vknewscompose.data.dto.news_feed.RepostsX
import com.kerencev.vknewscompose.data.dto.news_feed.SizeX

data class ProfilePhotosResponseDto(
    val response: ProfilePhotosDto?
)

data class ProfilePhotosDto(
    val count: Int?,
    val items: List<PhotoDto>?
)

data class PhotoDto(
    val id: Long?,
    val date: Long?,
    val lat: Double?,
    val long: Double?,
    val sizes: List<SizeX>?,
    val text: String?,
    val likes: Likes?,
    val reposts: RepostsX?

)
