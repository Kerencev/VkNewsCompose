package com.kerencev.vknewscompose.data.api

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.data.dto.friends.FriendsResponseDto
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.131")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("newsfeed.get?v=5.131")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): CommentsResponseDto

    @GET("users.get?v=5.131&fields=city, education, counters, photo_max")
    suspend fun getProfile(
        @Query("access_token") token: String,
        @Query("user_ids") usersIds: String
    ): ProfileResponseDto

    @GET("photos.getAll?v=5.131&extended=true")
    suspend fun getProfilePhotos(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: String
    ): ProfilePhotosResponseDto

    @GET("wall.get?v=5.131&extended=true")
    suspend fun getWall(
        @Query("access_token") token: String,
        @Query("owner_id") userId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): NewsFeedResponseDto

    @GET("friends.search?v=5.131&fields=photo_200, online")
    suspend fun getFriends(
        @Query("access_token") token: String,
        @Query("user_id") userId: String,
        @Query("q") searchText: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): FriendsResponseDto

}