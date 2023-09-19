package com.kerencev.vknewscompose.presentation.common.views.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun ProgressBarDefault(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = LightBlue)
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarDefaultPreview() {
    VkNewsComposeTheme {
        ProgressBarDefault(modifier = Modifier.fillMaxSize())
    }
}