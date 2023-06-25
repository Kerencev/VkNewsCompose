package com.kerencev.vknewscompose.presentation.mapper

import com.kerencev.vknewscompose.domain.entities.NewsModel
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

interface NewsModelMapper {

    fun mapToUi(newsModel: NewsModel): NewsModelUi

    fun mapToEntity(newsModelUi: NewsModelUi): NewsModel
}