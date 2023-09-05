package com.kerencev.vknewscompose.presentation.screens.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.mvi.state.SearchViewState
import com.kerencev.vknewscompose.presentation.common.views.icon.IconBack
import com.kerencev.vknewscompose.presentation.common.views.loading.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.toolbar.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.text.TextTotalCount
import com.kerencev.vknewscompose.presentation.common.views.text.TextWithButton
import com.kerencev.vknewscompose.presentation.common.views.profile_item.UserItem
import com.kerencev.vknewscompose.presentation.common.views.search.SearchLayout
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsEvent

@Composable
fun FriendsScreen(
    userId: Long,
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    onFriendClick: (userId: Long) -> Unit,
) {
    val component = getApplicationComponent()
        .getFriendsScreenComponentFactory()
        .create(userId)
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.observedState.collectAsState()
    val sendEvent: (FriendsEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    FriendsScreenContent(
        currentState = state,
        paddingValues = paddingValues,
        onBackPressed = onBackPressed,
        sendEvent = sendEvent,
        onFriendClick = onFriendClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreenContent(
    currentState: State<SearchViewState<UserProfileModel>>,
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    sendEvent: (FriendsEvent) -> Unit,
    onFriendClick: (userId: Long) -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
        paddingValues = paddingValues,
        elevation = 0.dp,
        toolBarTitle = { Text(text = stringResource(id = R.string.friends)) },
        toolBarNavigationIcon = { IconBack(onBackPressed = onBackPressed) }
    ) { innerPadding ->
        val state = currentState.value

        SearchLayout(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            swipeRefreshState = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
            onRefresh = { sendEvent(FriendsEvent.GetFriends(isRefresh = true)) },
            onSearch = { sendEvent(FriendsEvent.SearchFriends(searchText = it)) },
            inputLabel = { Text(text = stringResource(id = R.string.search_friends_label)) },
        ) {
            items(state.items, { it.id }) { friendModel ->
                UserItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .clickable { onFriendClick(friendModel.id) },
                    user = friendModel
                )
            }
            item {
                when {
                    state.isLoading -> ProgressBarDefault(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )

                    state.errorMessage != null -> TextWithButton(
                        modifier = Modifier.padding(16.dp),
                        title = stringResource(id = R.string.load_data_error),
                        onClick = { sendEvent(FriendsEvent.GetFriends(isRefresh = false)) }
                    )

                    state.isItemsOver -> TextTotalCount(count = state.items.size)

                    !state.isSwipeRefreshing -> SideEffect {
                        sendEvent(FriendsEvent.GetFriends(isRefresh = state.items.isEmpty()))
                    }
                }
            }
        }
    }
}