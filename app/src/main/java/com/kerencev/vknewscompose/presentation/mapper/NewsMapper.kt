package com.kerencev.vknewscompose.presentation.mapper

import com.kerencev.vknewscompose.domain.entities.ImageContentModel
import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.ImageContentModelUi
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

fun NewsModel.mapToUiModel(): NewsModelUi {
    return NewsModelUi(
        id = id,
        communityId = communityId,
        communityName = communityName,
        postTime = postTime,
        communityImageUrl = communityImageUrl,
        contentText = contentText,
        imageContent = imageContent.map { it.mapToUiModel() },
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
        imageContent = imageContent.map { it.mapToModel() },
        viewsCount = viewsCount,
        sharesCount = sharesCount,
        commentsCount = commentsCount,
        likesCount = likesCount,
        isLiked = isLiked
    )
}

fun ImageContentModel.mapToUiModel(): ImageContentModelUi {
    return ImageContentModelUi(
        id = id,
        url = url,
        height = height,
        width = width
    )
}

fun ImageContentModelUi.mapToModel(): ImageContentModel {
    return ImageContentModel(
        id = id,
        url = url,
        height = height,
        width = width
    )
}