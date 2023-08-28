package com.kerencev.vknewscompose.presentation.screens.news.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsInputAction
import com.kerencev.vknewscompose.presentation.screens.news.flow.NewsViewState

/**
 * Feature for liking or disliking a post
 */
interface ChangeLikeStatusFeature : VkFeature<NewsInputAction.ChangeLikeStatus, NewsViewState>