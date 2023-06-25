package com.kerencev.vknewscompose.data.mapper.news_feed

import com.kerencev.vknewscompose.data.dto.news_feed.NewsFeedResponseDto
import com.kerencev.vknewscompose.domain.entities.NewsModel

interface NewsFeedMapper {

    fun mapToEntity(responseDto: NewsFeedResponseDto): List<NewsModel>
}