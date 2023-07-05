package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kerencev.vknewscompose.ui.theme.LightGray
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerDefault(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shimmer()
            .background(LightGray)
    )
}