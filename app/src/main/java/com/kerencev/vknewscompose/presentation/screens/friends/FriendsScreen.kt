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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.clearFocusOnKeyboardDismiss
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.views.BaseTextInput
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsEvent
import com.kerencev.vknewscompose.presentation.screens.friends.flow.FriendsViewState
import com.kerencev.vknewscompose.presentation.screens.friends.views.FriendItem
import com.kerencev.vknewscompose.ui.theme.LightBlue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FriendsScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    onFriendClick: () -> Unit,
) {
    val viewModel: FriendsViewModel = viewModel(factory = viewModelFactory)
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
    onFriendClick: () -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
        paddingValues = paddingValues,
        elevation = 0.dp,
        toolBarTitle = { Text(text = stringResource(id = R.string.friends)) },
        toolBarNavigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = LightBlue
                )
            }
        }
    ) { innerPadding ->
        val state = currentState.value
        var searchText by rememberSaveable { mutableStateOf("") }
        var searchJob by remember { mutableStateOf<Job?>(null) }
        LaunchedEffect(searchText) {
            if (searchText.isEmpty() && state.friendsList.isEmpty() && !state.isFriendsOver) return@LaunchedEffect
            searchJob?.cancel()
            searchJob = launch {
                delay(300)
                sendEvent(
                    FriendsEvent.GetFriends(
                        searchText = searchText,
                        isRefresh = true
                    )
                )
            }
        }

        SwipeRefresh(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
            onRefresh = {
                sendEvent(
                    FriendsEvent.GetFriends(
                        searchText = searchText,
                        isRefresh = true
                    )
                )
            },
            indicatorPadding = PaddingValues(top = 72.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth()
                    ) {
                        BaseTextInput(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                                .clearFocusOnKeyboardDismiss(),
                            value = searchText,
                            onValueChange = { searchText = it },
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
                            .clickable { onFriendClick() },
                        friendModel = friendModel
                    )
                }
                item {
                    when {
                        state.isLoading -> ProgressBarDefault(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        )

                        state.errorMessage != null -> TextWithButton(
                            modifier = Modifier.padding(16.dp),
                            title = stringResource(id = R.string.load_data_error),
                            onClick = {
                                sendEvent(
                                    FriendsEvent.GetFriends(
                                        searchText = searchText,
                                        isRefresh = false
                                    )
                                )
                            }
                        )

                        state.isFriendsOver -> Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                            text = stringResource(
                                id = R.string.set_count,
                                state.friendsList.size
                            ),
                            color = LightBlue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )

                        else -> SideEffect {
                            sendEvent(
                                FriendsEvent.GetFriends(
                                    searchText = searchText,
                                    isRefresh = state.friendsList.isEmpty()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}