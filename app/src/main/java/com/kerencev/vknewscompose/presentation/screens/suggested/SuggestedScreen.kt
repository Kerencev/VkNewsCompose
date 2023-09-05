package com.kerencev.vknewscompose.presentation.screens.suggested

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.profile_item.ProfileItem
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedEvent
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedShot
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SuggestedScreen(
    viewModelFactory: ViewModelFactory,
    onItemClick: (params: ProfileParams) -> Unit,
) {
    val viewModel: SuggestedViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()
    val shot = viewModel.observedShot.collectAsState(initial = SuggestedShot.None)
    val sendEvent: (SuggestedEvent) -> Unit = rememberUnitParams { viewModel.send(it) }
    val scope = rememberCoroutineScope()

    SuggestedScreenContent(
        currentState = state,
        currentShot = shot,
        sendEvent = sendEvent,
        scope = scope,
        onItemClick = onItemClick,
    )
}

@Composable
fun SuggestedScreenContent(
    currentState: State<SuggestedViewState>,
    currentShot: State<SuggestedShot>,
    sendEvent: (SuggestedEvent) -> Unit,
    scope: CoroutineScope,
    onItemClick: (params: ProfileParams) -> Unit
) {
    val state = currentState.value
    SwipeRefresh(
        state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
        onRefresh = { sendEvent(SuggestedEvent.GetData(isRefreshing = true)) },
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(
                items = state.items,
                key = { it.id }
            ) { profile ->
                ProfileItem(
                    profile = profile,
                    onClick = { onItemClick(ProfileParams(id = profile.id, type = profile.type)) }
                )
            }
        }

        when (currentShot.value) {
            is SuggestedShot.ScrollToTop -> SideEffect {
                scope.launch {
                    listState.animateScrollToItem(0)
                    sendEvent(SuggestedEvent.OnScrollToTop)
                }
            }

            is SuggestedShot.None -> Unit
        }
    }
}