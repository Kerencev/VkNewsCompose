package com.kerencev.vknewscompose.domain.entities

data class WallModel(
    val items: List<NewsModel>,
    val isItemsOver: Boolean
)
