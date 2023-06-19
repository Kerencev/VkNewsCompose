package com.kerencev.vknewscompose.data.dto.likes

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes: LikesCountDto?
)

data class LikesCountDto(
    @SerializedName("likes") val count: Int?
)