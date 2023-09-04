package com.kerencev.vknewscompose.presentation.common.views.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kerencev.vknewscompose.presentation.common.compose.clearFocusOnKeyboardDismiss
import com.kerencev.vknewscompose.presentation.common.views.DelayTextInput

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchLayout(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    onSearch: (String) -> Unit,
    inputLabel: @Composable (() -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    SwipeRefresh(
        modifier = modifier,
        state = swipeRefreshState,
        onRefresh = onRefresh,
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
                        onSearch = onSearch,
                        label = inputLabel,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
            content()
        }
    }
}