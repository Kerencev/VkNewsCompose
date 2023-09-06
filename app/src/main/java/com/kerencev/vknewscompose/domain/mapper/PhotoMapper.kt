package com.kerencev.vknewscompose.domain.mapper

import com.kerencev.vknewscompose.domain.entities.ImageContentModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel

fun ImageContentModel.toPhotoModel() = PhotoModel(
    id = id,
    date = null,
    lat = null,
    long = null,
    url = url,
    height = height,
    width = width,
    text = "",
    likes = 0,
    reposts = 0
)