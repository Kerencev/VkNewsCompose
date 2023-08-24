package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.views.ProgressBarDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.ui.theme.LightBlue

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

        isWallPostOver -> Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.set_count, totalWallItemsCount),
            color = LightBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        else -> SideEffect { onScrollToBottom() }
    }
}