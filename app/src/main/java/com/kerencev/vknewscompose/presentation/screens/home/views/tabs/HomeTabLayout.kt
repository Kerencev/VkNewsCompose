package com.kerencev.vknewscompose.presentation.screens.home.views.tabs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.kerencev.vknewscompose.ui.theme.LightBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTabLayout(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        divider = { Spacer(modifier = Modifier.height(4.dp)) },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = LightBlue
            )
        },
    ) {
        HomeTab.values().forEachIndexed { index, newsType ->
            val isSelected = pagerState.currentPage == index
            val visibilityState by animateFloatAsState(targetValue = if (isSelected) 1f else 0.5f)
            Row {
                Tab(
                    modifier = Modifier.background(MaterialTheme.colors.surface),
                    selected = isSelected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            modifier = Modifier.alpha(visibilityState),
                            text = newsType.toTitle(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
    }
}
