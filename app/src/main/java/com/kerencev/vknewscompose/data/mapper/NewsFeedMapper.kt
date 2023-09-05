package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.news_feed.AttachmentType
import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.domain.entities.ImageContentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.extensions.toDateTime
import kotlin.math.absoluteValue

fun NewsFeedResponseDto.mapToModel(): List<NewsModel> {
    val result = mutableListOf<NewsModel>()

    val posts = this.response?.items
    val groups = this.response?.groups
    val profiles = this.response?.profiles
    val uniquePostsId = HashSet<Long>()

    posts?.let {
        for (post in posts) {
            val group = groups?.firstOrNull() { it.id == post.ownerId?.absoluteValue }
            val profile = profiles?.firstOrNull() { it.id == post.ownerId?.absoluteValue }
            val imageContent = post.attachments
                ?.filter { it.type == AttachmentType.photo }
                ?.map {
                    val photo = it.photo?.sizes?.lastOrNull()
                    ImageContentModel(
                        id = it.photo?.id ?: 0,
                        url = photo?.url.orEmpty(),
                        height = photo?.height ?: 0,
                        width = photo?.width ?: 0
                    )
                } ?: emptyList()

            if (post.id == null || uniquePostsId.contains(post.id)) continue
            uniquePostsId.add(post.id)
            val newsModel = NewsModel(
                id = post.id,
                type = if (profile != null) ProfileType.USER else ProfileType.GROUP,
                ownerId = post.ownerId ?: 0,
                communityName = group?.name ?: "${profile?.firstName} ${profile?.lastName}",
                postTime = ((post.date ?: 0) * 1000).toDateTime(),
                communityImageUrl = group?.avatar ?: profile?.photoUrl,
                contentText = post.text.orEmpty(),
                imageContent = imageContent,
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