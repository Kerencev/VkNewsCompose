package com.kerencev.vknewscompose.data.dto.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponseDto(
    val response: List<ProfileDto>?
)

data class ProfileDto(
    val id: Long?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    val city: City?,
    @SerializedName("university_name") val universityName: String?,
    val counters: Counters?,
    @SerializedName("photo_200") val avatar200: String?,
    @SerializedName("photo_100") val avatar100: String?,
    val online: Int?,
    @SerializedName("online_mobile") val onlineMobile: Int?,
    @SerializedName("last_seen") val lastSeen: LastSeenDto?,
)

data class City(
    val id: Int?,
    val title: String?
)

data class Counters(
    val albums: Int?,
    val audios: Int?,
    @SerializedName("clips_followers") val clipsFollowers: Int?,
    val followers: Int?,
    val friends: Int?,
    val gifts: Int?,
    val groups: Int?,
    @SerializedName("new_photo_tags") val newPhotoTags: Int?,
    @SerializedName("new_recognition_tags") val newRecognitionTags: Int?,
    @SerializedName("online_friends") val onlineFriends: Int?,
    val pages: Int?,
    val photos: Int?,
    val subscriptions: Int?,
    @SerializedName("user_photos") val userPhotos: Int?,
    @SerializedName("video_playlists") val videoPlaylists: Int?,
    val videos: Int?
)

data class LastSeenDto(
    val platform: Int?,
    val time: Long?
)