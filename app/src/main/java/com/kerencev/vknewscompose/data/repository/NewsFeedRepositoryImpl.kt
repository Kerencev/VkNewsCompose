package com.kerencev.vknewscompose.data.repository

import com.kerencev.vknewscompose.data.api.ApiService
import com.kerencev.vknewscompose.data.mapper.mapToModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.domain.repositories.NewsFeedRepository
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKKeyValueStorage,
) : NewsFeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private var startFrom: String? = null
    private val newsCache = mutableListOf<NewsModel>()

    override fun getNewsFeed(isRefresh: Boolean) = flow {
        if (isRefresh) {
            startFrom = null
            newsCache.clear()
        }
        val response = if (startFrom == null) apiService.loadNewsFeed(getAccessToken())
        else apiService.loadNewsFeed(getAccessToken(), startFrom.orEmpty())
        startFrom = response.response?.nextFrom
        val news = response.mapToModel()
        newsCache.addAll(news)
        emit(newsCache)
    }

    override fun changeLikeStatus(newsModel: NewsModel) = flow {
        if (newsModel.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(newsModel.copy(likesCount = newsModel.likesCount - 1, isLiked = false))
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = newsModel.communityId, postId = newsModel.id
            )
            emit(newsModel.copy(likesCount = newsModel.likesCount + 1, isLiked = true))
        }
    }

    override fun getPostPhotosById(newsModelId: Long): Flow<List<PhotoModel>> = flow {
        val post = newsCache.firstOrNull { it.id == newsModelId }
        if (post == null) emit(emptyList())
        else {
            emit(
                post.imageContent.map { image ->
                    PhotoModel(
                        id = image.id,
                        date = null,
                        lat = null,
                        long = null,
                        url = image.url,
                        height = image.height,
                        width = image.width,
                        text = "",
                        likes = 0,
                        reposts = 0
                    )
                }
            )
        }
    }

    @Throws(IllegalStateException::class)
    private fun getAccessToken(): String {
        return token?.accessToken ?: error("Token is null")
    }

}