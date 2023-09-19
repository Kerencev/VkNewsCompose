package com.kerencev.vknewscompose.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kerencev.vknewscompose.R

@Composable
fun Int.getFriendWord(): String {
    return stringResource(
        id = when {
            this % 10 == 1 && this % 100 != 11 -> R.string.friend
            this % 10 in 2..4 && this % 100 !in 12..14 -> R.string.friends_a_little
            else -> R.string.friends_a_lot
        }
    )
}