package com.kerencev.vknewscompose.data.mapper

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.domain.entities.CommentModel
import com.kerencev.vknewscompose.domain.entities.ProfileType
import com.kerencev.vknewscompose.extensions.toDateTime

fun CommentsResponseDto.mapToModel(): List<CommentModel> {
    val result = mutableListOf<CommentModel>()

    val comments = this.response?.items
    val profiles = this.response?.profiles

    comments?.let {
        for (comment in comments) {
            val profile = profiles?.firstOrNull() { it.id == comment.fromId }
            val commentModel = CommentModel(
                id = comment.id,
                fromId = comment.fromId ?: 0,
                authorName = "${profile?.firstName} ${profile?.lastName}",
                authorImageUrl = profile?.avatar200 ?: profile?.avatar100,
                commentText = comment.text.orEmpty(),
                commentDate = ((comment.date ?: 0) * 1000).toDateTime(),
                type = if (profile != null) ProfileType.USER else ProfileType.GROUP,
            )
            result.add(commentModel)
        }
    }
    return result.toList()
}