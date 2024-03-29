package com.kerencev.vknewscompose.data.api

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.data.dto.friends.FriendsResponseDto
import com.kerencev.vknewscompose.data.dto.group.GroupProfileResponseDto
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfilePhotosResponseDto
import com.kerencev.vknewscompose.data.dto.profile.ProfileResponseDto
import com.kerencev.vknewscompose.data.dto.search.SearchResponseDto
import com.kerencev.vknewscompose.data.dto.suggested.SuggestedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.131")
    suspend fun loadNewsFeed(): NewsFeedResponseDto

    @GET("newsfeed.get?v=5.131")
    suspend fun loadNewsFeed(
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommended(): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommended(
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_200")
    suspend fun getComments(
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): CommentsResponseDto

    @GET("users.get?v=5.131&fields=city, education, counters, photo_200, last_seen, online")
    suspend fun getUserProfile(
        @Query("user_ids") usersIds: String
    ): ProfileResponseDto

    @GET("groups.getById?v=5.131&fields=cover, description, members_count")
    suspend fun getGroupProfile(
        @Query("group_id") groupId: String
    ): GroupProfileResponseDto

    @GET("photos.getAll?v=5.131&extended=true")
    suspend fun getProfilePhotos(
        @Query("owner_id") ownerId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): ProfilePhotosResponseDto

    @GET("wall.get?v=5.131&extended=true&fields=photo_200")
    suspend fun getWall(
        @Query("owner_id") userId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): NewsFeedResponseDto

    @GET("friends.search?v=5.131&fields=photo_200, online, last_seen")
    suspend fun getFriends(
        @Query("user_id") userId: String,
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): FriendsResponseDto

    @GET("newsfeed.getSuggestedSources?v=5.131&fields=photo_200, online, last_seen, cover, members_count")
    suspend fun getSuggested(
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("shuffle") bool: Int = 1
    ): SuggestedResponseDto

    @GET("search.getHints?v=5.131&fields=photo_200, online, last_seen, cover, members_count")
    suspend fun search(
        @Query("search_global") searchGlobal: Int = 1,
        @Query("q") query: String,
        @Query("limit") count: Int,
        @Query("offset") offset: Int,
    ): SearchResponseDto

}