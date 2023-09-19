package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosDto
import com.kerencev.vknewscompose.domain.entities.PhotoModel

fun ProfilePhotosDto.mapToModel(): List<PhotoModel> {
    return items?.map {
        val lastSize = it.sizes?.lastOrNull()
        PhotoModel(
            id = it.id ?: 0,
            date = it.date,
            lat = it.lat,
            long = it.long,
            url = lastSize?.url.orEmpty(),
            height = lastSize?.height ?: 0,
            width = lastSize?.width ?: 0,
            text = it.text.orEmpty(),
            likes = it.likes?.count ?: 0,
            reposts = it.reposts?.count ?: 0
        )
    } ?: emptyList()
}