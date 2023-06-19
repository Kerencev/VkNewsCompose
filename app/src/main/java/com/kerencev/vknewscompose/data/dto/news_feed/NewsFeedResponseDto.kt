package com.kerencev.vknewscompose.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    val response: NewsFeedDto?
)

data class NewsFeedDto(
    val groups: List<Group>?,
    val items: List<ItemNewsDto>?,
    @SerializedName("next_from") val nextFrom: String?
)

data class Group(
    val id: Long?,
    val name: String?,
    @SerializedName("photo_200") val avatar: String?
)

data class ItemNewsDto(
    val id: Long?,
    val attachments: List<Attachment>?,
    val comments: Comments?,
    val date: Long?,
    val likes: Likes?,
    val reposts: RepostsX?,
    @SerializedName("source_id") val sourceId: Long?,
    val text: String?,
    val views: Views?
)

data class Attachment(
    val photo: PhotoX?
)

data class PhotoX(
    val sizes: List<SizeX>?
)

data class SizeX(
    val url: String?
)

data class Comments(
    val count: Int?
)

data class Likes(
    val count: Int?,
    @SerializedName("user_likes") val userLikes: Int?
)

data class RepostsX(
    val count: Int?
)

data class Views(
    val count: Int?
)