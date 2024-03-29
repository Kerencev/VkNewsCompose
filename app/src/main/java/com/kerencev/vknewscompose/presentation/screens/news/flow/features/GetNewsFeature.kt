package com.kerencev.vknewscompose.presentation.screens.news.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction

/**
 * Feature for getting a list of news
 */
interface GetNewsFeature : VkFeature<NewsInputAction.GetNews>