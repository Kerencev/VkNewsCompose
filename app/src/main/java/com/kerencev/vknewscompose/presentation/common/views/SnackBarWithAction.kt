package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SnackBarWithAction(
    modifier: Modifier = Modifier,
    text: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    Snackbar(
        modifier = modifier,
        action = {
            Text(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(8.dp),
                text = actionLabel
            )
        }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text
        )
    }
}