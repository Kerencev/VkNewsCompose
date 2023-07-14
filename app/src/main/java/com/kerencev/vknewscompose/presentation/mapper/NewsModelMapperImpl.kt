package com.kerencev.vknewscompose.presentation.mapper

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import javax.inject.Inject

class NewsModelMapperImpl @Inject constructor() : NewsModelMapper {

    override fun mapToUi(newsModel: NewsModel): NewsModelUi {
        return NewsModelUi(
            id = newsModel.id,
            communityId = newsModel.communityId,
            communityName = newsModel.communityName,
            postTime = newsModel.postTime,
            communityImageUrl = newsModel.communityImageUrl,
            contentText = newsModel.contentText,
            contentImageUrl = newsModel.contentImageUrl,
            contentImageHeight = newsModel.contentImageHeight,
            contentImageWidth = newsModel.contentImageWidth,
            viewsCount = newsModel.viewsCount,
            sharesCount = newsModel.sharesCount,
            commentsCount = newsModel.commentsCount,
            likesCount = newsModel.likesCount,
            isLiked = newsModel.isLiked
        )
    }

    override fun mapToEntity(newsModelUi: NewsModelUi): NewsModel {
        return NewsModel(
            id = newsModelUi.id,
            communityId = newsModelUi.communityId,
            communityName = newsModelUi.communityName,
            postTime = newsModelUi.postTime,
            communityImageUrl = newsModelUi.communityImageUrl,
            contentText = newsModelUi.contentText,
            contentImageUrl = newsModelUi.contentImageUrl,
            contentImageHeight = newsModelUi.contentImageHeight,
            contentImageWidth = newsModelUi.contentImageWidth,
            viewsCount = newsModelUi.viewsCount,
            sharesCount = newsModelUi.sharesCount,
            commentsCount = newsModelUi.commentsCount,
            likesCount = newsModelUi.likesCount,
            isLiked = newsModelUi.isLiked
        )
    }
}