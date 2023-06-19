package com.kerencev.vknewscompose.data.dto.comments

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    val id: Long?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("photo_100") val avatar: String?
)