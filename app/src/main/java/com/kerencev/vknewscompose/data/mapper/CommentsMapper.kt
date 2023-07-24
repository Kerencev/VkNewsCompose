package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.extensions.toDateTime

fun CommentsResponseDto.mapToModel(): List<CommentModel> {
    val result = mutableListOf<CommentModel>()

    val comments = this.response?.items
    val profiles = this.response?.profiles

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