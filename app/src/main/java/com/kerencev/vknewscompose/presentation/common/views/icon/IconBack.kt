package com.kerencev.vknewscompose.presentation.common.views.icon

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun IconBack(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onBackPressed() }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = LightBlue
        )
    }
}