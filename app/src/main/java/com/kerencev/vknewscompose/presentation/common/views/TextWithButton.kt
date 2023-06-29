package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun TextWithButton(
    modifier: Modifier = Modifier,
    title: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue),
            onClick = { onRetryClick() }
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithButtonPreview() {
    VkNewsComposeTheme {
        TextWithButton(
            modifier = Modifier.padding(16.dp),
            title = stringResource(id = R.string.load_data_error),
            onRetryClick = {}
        )
    }
}