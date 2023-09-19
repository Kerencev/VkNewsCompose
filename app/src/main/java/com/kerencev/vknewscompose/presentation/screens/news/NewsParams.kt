package com.kerencev.vknewscompose.presentation.screens.news

data class NewsParams(
    val type: NewsType
)

enum class NewsType {
    NEWS, RECOMMENDATION,
}
