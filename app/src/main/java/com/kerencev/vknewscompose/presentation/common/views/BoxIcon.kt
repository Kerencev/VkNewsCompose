package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BoxIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }
}