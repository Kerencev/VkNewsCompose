package com.kerencev.vknewscompose.presentation.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.GroupProfileModel
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.domain.entities.UserProfileModel
import com.kerencev.vknewscompose.presentation.common.compose.clearFocusOnKeyboardDismiss
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.mvi.state.SearchViewState
import com.kerencev.vknewscompose.presentation.common.views.DelayTextInput
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.presentation.model.ProfileType
import com.kerencev.vknewscompose.presentation.screens.friends.views.FriendItem
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchEvent
import com.kerencev.vknewscompose.presentation.screens.search.views.SearchImage
import com.kerencev.vknewscompose.presentation.screens.suggested.views.GroupItem
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun SearchScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onItemClick: (params: ProfileParams) -> Unit,
) {
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()
    val sendEvent: (SearchEvent) -> Unit = rememberUnitParams { viewModel.send(it) }

    SearchScreenContent(
        currentState = state,
        paddingValues = paddingValues,
        sendEvent = sendEvent,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreenContent(
    currentState: State<SearchViewState<Profile>>,
    paddingValues: PaddingValues,
    sendEvent: (event: SearchEvent) -> Unit,
    onItemClick: (params: ProfileParams) -> Unit,
) {
    //TODO: вынести общее с FriendsScreen
    ScaffoldWithCollapsingToolbar(
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
        paddingValues = paddingValues,
        elevation = 0.dp,
        toolBarTitle = { Text(text = stringResource(id = R.string.global_search)) },
    ) { innerPadding ->
        val state = currentState.value

        SwipeRefresh(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
            onRefresh = { sendEvent(SearchEvent.Refresh) },
            indicatorPadding = PaddingValues(top = 72.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
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
                                onSearch = {
                                    sendEvent(SearchEvent.Search(query = it))
                                },
                                label = { Text(text = stringResource(id = R.string.search_global_label)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }

                    items(state.items, { it.id }) { profile ->
                        Spacer(modifier = Modifier.height(8.dp))
                        when (profile) {
                            is UserProfileModel -> FriendItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(
                                            ProfileParams(
                                                id = profile.id,
                                                type = ProfileType.USER
                                            )
                                        )
                                    },
                                user = profile
                            )

                            is GroupProfileModel -> GroupItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(
                                            ProfileParams(
                                                id = profile.id,
                                                type = ProfileType.GROUP
                                            )
                                        )
                                    },
                                model = profile
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
                                onClick = { sendEvent(SearchEvent.Retry) }
                            )

                            state.isItemsOver -> Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                                text = stringResource(id = R.string.set_count, state.items.size),
                                color = LightBlue,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                            )

                            state.items.isEmpty() -> SearchImage(
                                modifier = Modifier.padding(
                                    top = 64.dp,
                                    start = 40.dp,
                                    end = 40.dp
                                )
                            )

                            !state.isSwipeRefreshing -> SideEffect {
                                sendEvent(SearchEvent.GetNextPage)
                            }
                        }
                    }
                }
            }
        }
    }
}