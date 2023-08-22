package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithCollapsingToolbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    paddingValues: PaddingValues = PaddingValues(),
    elevation: Dp = 8.dp,
    toolBarTitle: @Composable () -> Unit,
    toolBarNavigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(paddingValues),
        topBar = {
            Surface(elevation = elevation) {
                TopAppBar(
                    windowInsets = WindowInsets.systemBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                    title = toolBarTitle,
                    navigationIcon = toolBarNavigationIcon,
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colors.surface,
                        scrolledContainerColor = MaterialTheme.colors.surface,
                        titleContentColor = MaterialTheme.colors.onPrimary
                    )
                )
            }
        },
        content = content
    )
}