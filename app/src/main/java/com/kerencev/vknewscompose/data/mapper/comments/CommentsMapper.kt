package com.kerencev.vknewscompose.data.mapper.comments

import com.kerencev.vknewscompose.data.dto.comments.CommentsResponseDto
import com.kerencev.vknewscompose.domain.entities.CommentModel

interface CommentsMapper {

    fun mapToEntity(responseDto: CommentsResponseDto): List<CommentModel>
}