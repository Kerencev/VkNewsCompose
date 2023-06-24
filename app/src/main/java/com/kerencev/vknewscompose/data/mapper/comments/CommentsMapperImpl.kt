package com.kerencev.vknewscompose.data.mapper.comments

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.extensions.toDateTime
import javax.inject.Inject

class CommentsMapperImpl @Inject constructor() : CommentsMapper {

    override fun mapToEntity(responseDto: CommentsResponseDto): List<CommentModel> {
        val result = mutableListOf<CommentModel>()

        val comments = responseDto.response?.items
        val profiles = responseDto.response?.profiles

        comments?.let {
            for (comment in comments) {
                val profile = profiles?.firstOrNull() { it.id == comment.fromId } ?: continue
                val commentModel = CommentModel(
                    id = comment.id,
                    authorName = "${profile.firstName} ${profile.lastName}",
                    authorImageUrl = profile.avatar,
                    commentText = comment.text.orEmpty(),
                    commentDate = ((comment.date ?: 0) * 1000).toDateTime()
                )
                result.add(commentModel)
            }
        }
        return result.toList()
    }
}