package com.kerencev.vknewscompose.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    val response: NewsFeedDto?
)

data class NewsFeedDto(
    val count: Int?,
    val groups: List<Group>?,
    val items: List<ItemNewsDto>?,
    val profiles: List<Profile>?,
    @SerializedName("next_from") val nextFrom: String?
)

data class Profile(
    val id: Long?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("photo_100") val photoUrl: String?
)

data class Group(
    val id: Long?,
    val name: String?,
    @SerializedName("photo_200") val avatar: String?
)

data class ItemNewsDto(
    val id: Long?,
    @SerializedName("from_id") val fromId: Long?,
    @SerializedName("owner_id") val ownerId: Long?,
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
    val height: Int?,
    val width: Int?,
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