package com.kerencev.vknewscompose.presentation.screens.search

import androidx.compose.foundation.layout.PaddingValues
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
import com.kerencev.vknewscompose.di.common.ViewModelFactory
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.common.mvi.state.SearchViewState
import com.kerencev.vknewscompose.presentation.common.views.loading.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.toolbar.ScaffoldWithCollapsingToolbar
import com.kerencev.vknewscompose.presentation.common.views.text.TextTotalCount
import com.kerencev.vknewscompose.presentation.common.views.text.TextWithButton
import com.kerencev.vknewscompose.presentation.common.views.profile_item.ProfileItem
import com.kerencev.vknewscompose.presentation.common.views.search.SearchLayout
import com.kerencev.vknewscompose.presentation.screens.profile.ProfileParams
import com.kerencev.vknewscompose.presentation.screens.search.flow.SearchEvent
import com.kerencev.vknewscompose.presentation.screens.search.views.SearchImage

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    currentState: State<SearchViewState<Profile>>,
    paddingValues: PaddingValues,
    sendEvent: (event: SearchEvent) -> Unit,
    onItemClick: (params: ProfileParams) -> Unit,
) {
    ScaffoldWithCollapsingToolbar(
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
        paddingValues = paddingValues,
        elevation = 0.dp,
        toolBarTitle = { Text(text = stringResource(id = R.string.global_search)) },
    ) { innerPadding ->
        val state = currentState.value

        SearchLayout(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            swipeRefreshState = SwipeRefreshState(isRefreshing = state.isSwipeRefreshing),
            onRefresh = { sendEvent(SearchEvent.Refresh) },
            onSearch = { sendEvent(SearchEvent.Search(query = it)) },
            inputLabel = { Text(text = stringResource(id = R.string.search_global_label)) },
        ) {
            items(state.items, { it.id }) { profile ->
                ProfileItem(
                    profile = profile,
                    onClick = { onItemClick(ProfileParams(id = profile.id, type = profile.type)) }
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
                        onClick = { sendEvent(SearchEvent.Retry) }
                    )

                    state.isItemsOver -> TextTotalCount(count = state.items.size)

                    state.items.isEmpty() -> SearchImage(
                        modifier = Modifier.padding(
                            top = 64.dp,
                            start = 40.dp,
                            end = 40.dp
                        )
                    )

                    !state.isSwipeRefreshing -> SideEffect { sendEvent(SearchEvent.GetNextPage) }
                }
            }
        }
    }
}