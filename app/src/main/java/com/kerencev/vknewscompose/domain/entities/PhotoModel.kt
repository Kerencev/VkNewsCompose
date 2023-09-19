package com.kerencev.vknewscompose.domain.entities

import kotlin.random.Random

open class Photo(open val id: Long)

data class PhotoModel(
    override val id: Long,
    val date: Long?,
    val lat: Double?,
    val long: Double?,
    val url: String,
    val height: Int,
    val width: Int,
    val text: String,
    val likes: Int,
    val reposts: Int
) : Photo(id)

class LoadingPhotoModel(
    override val id: Long = Random.nextLong(Long.MAX_VALUE)
) : Photo(id)

fun getDummyPhotos(size: Int): List<Photo> {
    return buildList {
        repeat(size) {
            add(LoadingPhotoModel())
        }
    }
}
