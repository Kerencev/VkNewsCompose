package com.kerencev.vknewscompose.data.repository

import android.app.Application
import android.util.Log
import com.kerencev.vknewscompose.data.api.ApiFactory
import com.kerencev.vknewscompose.data.mapper.CommentsMapper
import com.kerencev.vknewscompose.data.mapper.NewsFeedMapper
import com.kerencev.vknewscompose.data.dto.likes.LikesCountResponseDto
import com.kerencev.vknewscompose.domain.model.news_feed.CommentModel
import com.kerencev.vknewscompose.domain.model.news_feed.NewsModel
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _newsModels = mutableListOf<NewsModel>()
    val newsModels: List<NewsModel>
        get() = _newsModels.toList()

    private var nextFrom: String? = null

    suspend fun loadNewsFeed(): List<NewsModel> {
        val startFrom = nextFrom
        if (startFrom == null && newsModels.isNotEmpty()) return newsModels
        val response = if (startFrom == null) {
            apiService.loadNewsFeed(getAccessToken())
        } else {
            apiService.loadNewsFeed(getAccessToken(), startFrom)
        }
        nextFrom = response.response?.nextFrom
        val news = mapper.mapResponseToPost(response)
        _newsModels.addAll(news)
        return newsModels
    }

    suspend fun changeLikeStatus(newsModel: NewsModel) {
        val response: LikesCountResponseDto = if (newsModel.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
        }
        val newLikesCount = response.likes?.count
        val newPost = newsModel.copy(likesCount = newLikesCount ?: 0, isLiked = !newsModel.isLiked)
        val postIndex = _newsModels.indexOf(newsModel)
        _newsModels[postIndex] = newPost
    }

    private val commentsMapper = CommentsMapper()

    suspend fun getComments(newsModel: NewsModel): List<CommentModel> {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = newsModel.communityId,
            postId = newsModel.id
        )
        return commentsMapper.mapResponseToComments(response)
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

}