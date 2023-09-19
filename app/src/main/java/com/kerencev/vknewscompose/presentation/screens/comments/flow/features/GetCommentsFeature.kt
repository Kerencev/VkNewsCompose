package com.kerencev.vknewscompose.presentation.screens.comments.flow.features

import com.kerencev.vknewscompose.presentation.common.mvi.VkFeature
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsInputAction

/**
 * Get list of comments on a post
 */
interface GetCommentsFeature : VkFeature<CommentsInputAction.GetComments>