package com.kerencev.vknewscompose.presentation.mapper

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

fun NewsModel.mapToUiModel(): NewsModelUi {
    return NewsModelUi(
        id = id,
        communityId = communityId,
        communityName = communityName,
        postTime = postTime,
        communityImageUrl = communityImageUrl,
        contentText = contentText,
        contentImageUrl = contentImageUrl,
        contentImageHeight = contentImageHeight,
        contentImageWidth = contentImageWidth,
        viewsCount = viewsCount,
        sharesCount = sharesCount,
        commentsCount = commentsCount,
        likesCount = likesCount,
        isLiked = isLiked
    )
}

fun NewsModelUi.mapToModel(): NewsModel {
    return NewsModel(
        id = id,
        communityId = communityId,
        communityName = communityName,
        postTime = postTime,
        communityImageUrl = communityImageUrl,
        contentText = contentText,
        contentImageUrl = contentImageUrl,
        contentImageHeight = contentImageHeight,
        contentImageWidth = contentImageWidth,
        viewsCount = viewsCount,
        sharesCount = sharesCount,
        commentsCount = commentsCount,
        likesCount = likesCount,
        isLiked = isLiked
    )
}