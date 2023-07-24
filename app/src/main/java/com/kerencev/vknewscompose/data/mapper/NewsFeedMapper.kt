package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.extensions.toDateTime
import kotlin.math.absoluteValue

fun NewsFeedResponseDto.mapToModel(): List<NewsModel> {
    val result = mutableListOf<NewsModel>()

    val posts = this.response?.items
    val groups = this.response?.groups
    val profile = this.response?.profiles?.firstOrNull()
    val uniquePostsId = HashSet<Long>()

    posts?.let {
        for (post in posts) {
            val group = groups?.firstOrNull() { it.id == post.sourceId?.absoluteValue }
            val contentImage = post.attachments?.firstOrNull()?.photo?.sizes?.lastOrNull()
            if (post.id == null || uniquePostsId.contains(post.id)) continue
            uniquePostsId.add(post.id)
            val newsModel = NewsModel(
                id = post.id,
                communityId = post.sourceId ?: 0,
                communityName = group?.name
                    ?: if (profile?.firstName != null && profile.lastName != null)
                        "${profile.firstName} ${profile.lastName}" else "",
                postTime = ((post.date ?: 0) * 1000).toDateTime(),
                communityImageUrl = group?.avatar ?: profile?.photoUrl,
                contentText = post.text.orEmpty(),
                contentImageUrl = contentImage?.url,
                contentImageHeight = contentImage?.height,
                contentImageWidth = contentImage?.width,
                viewsCount = post.views?.count ?: 0,
                sharesCount = post.reposts?.count ?: 0,
                commentsCount = post.comments?.count ?: 0,
                likesCount = post.likes?.count ?: 0,
                isLiked = post.likes?.userLikes != 0
            )
            result.add(newsModel)
        }
    }
    return result
}