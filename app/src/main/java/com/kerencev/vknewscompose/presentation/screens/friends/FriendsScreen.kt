package com.kerencev.vknewscompose.presentation.screens.friends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.presentation.common.compose.clearFocusOnKeyboardDismiss
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.DelayTextInput
import com.kerencev.vknewscompose.presentation.common.views.IconBack
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsEvent
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsViewState
import com.kerencev.vknewscompose.presentation.screens.friends.views.FriendItem
import com.kerencev.vknewscompose.ui.theme.LightBlue

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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreenContent(
    currentState: State<FriendsViewState>,
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

        SwipeRefresh(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
            onRefresh = { sendEvent(FriendsEvent.GetFriends(isRefresh = true)) },
            indicatorPadding = PaddingValues(top = 72.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth()
                    ) {
                        DelayTextInput(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                                .clearFocusOnKeyboardDismiss(),
                            onSearch = { sendEvent(FriendsEvent.SearchFriends(searchText = it)) },
                            label = { Text(text = stringResource(id = R.string.search_friends_label)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                items(state.friendsList, { it.id }) { friendModel ->
                    FriendItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .clickable { onFriendClick(friendModel.id) },
                        friendModel = friendModel
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

                        state.isFriendsOver -> Text(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                            text = stringResource(
                                id = R.string.set_count,
                                state.friendsList.size
                            ),
                            color = LightBlue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                        )

                        !state.isSwipeRefreshing -> SideEffect {
                            sendEvent(
                                FriendsEvent.GetFriends(isRefresh = state.friendsList.isEmpty())
                            )
                        }
                    }
                }
            }
        }
    }
}