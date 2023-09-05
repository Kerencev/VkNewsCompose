package com.kerencev.vknewscompose.presentation.common.views.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun TextTotalCount(
    modifier: Modifier = Modifier,
    count: Int,
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        text = stringResource(id = R.string.set_count, count),
        color = LightBlue,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
}