package com.kerencev.vknewscompose.presentation.screens.comments

import com.kerencev.vknewscompose.presentation.common.mvi.BaseViewModel
import com.kerencev.vknewscompose.presentation.common.mvi.VkAction
import com.kerencev.vknewscompose.presentation.common.mvi.VkCommand
import com.kerencev.vknewscompose.presentation.common.mvi.VkEffect
import com.kerencev.vknewscompose.presentation.common.mvi.VkShot
import com.kerencev.vknewscompose.presentation.mapper.NewsModelMapper
import com.kerencev.vknewscompose.presentation.model.NewsModelUi
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsEvent
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsInputAction
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsOutputAction
import com.kerencev.vknewscompose.presentation.screens.comments.flow.CommentsViewState
import com.kerencev.vknewscompose.presentation.screens.comments.flow.features.GetCommentsFeature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    newsModel: NewsModelUi,
    private val getCommentsFeature: GetCommentsFeature,
    private val newsModelMapper: NewsModelMapper
) : BaseViewModel<CommentsEvent, CommentsViewState, VkShot>() {

    init {
        send(CommentsEvent.GetComments(newsModel))
    }

    override fun initState() = CommentsViewState()

    override fun produceCommand(event: CommentsEvent): VkCommand {
        return when (event) {
            is CommentsEvent.GetComments -> CommentsInputAction.GetComments(
                newsModelMapper.mapToEntity(
                    event.newsModel
                )
            )
        }
    }

    override fun features(action: VkAction): Flow<VkCommand>? {
        return when (action) {
            is CommentsInputAction.GetComments -> getCommentsFeature(action, state())
            else -> null
        }
            ?.flowOn(Dispatchers.IO)
    }

    override suspend fun produceShot(effect: VkEffect) = Unit

    override suspend fun produceState(action: VkAction) {
        when (action) {
            is CommentsOutputAction.SetComments -> setState { comments(action.result) }
            is CommentsOutputAction.Loading -> setState { loading() }
            is CommentsOutputAction.Error -> setState { error() }
        }
    }

}