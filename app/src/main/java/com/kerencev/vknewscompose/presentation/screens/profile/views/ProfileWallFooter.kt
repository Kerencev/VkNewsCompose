package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.views.loading.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.text.TextTotalCount
import com.kerencev.vknewscompose.presentation.common.views.text.TextWithButton

@Composable
fun ProfileWallFooter(
    isWallLoading: Boolean,
    errorMessage: String?,
    isWallPostOver: Boolean,
    onScrollToBottom: () -> Unit,
    totalWallItemsCount: Int
) {
    when {
        isWallLoading -> ProgressBarDefault(
            modifier = Modifier.padding(
                16.dp
            )
        )

        errorMessage != null -> TextWithButton(
            modifier = Modifier.padding(16.dp),
            title = stringResource(id = R.string.load_data_error),
            onClick = onScrollToBottom
        )

        isWallPostOver -> TextTotalCount(count = totalWallItemsCount)

        else -> SideEffect { onScrollToBottom() }
    }
}