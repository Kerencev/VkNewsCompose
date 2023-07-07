package com.kerencev.vknewscompose.presentation.screens.comments.flow

import com.kerencev.vknewscompose.presentation.common.mvi.VkEvent
import com.kerencev.vknewscompose.presentation.model.NewsModelUi

sealed class CommentsEvent : VkEvent {

    /**
     * Get list of comments
     */
    class GetComments(val newsModel: NewsModelUi) : CommentsEvent()

}