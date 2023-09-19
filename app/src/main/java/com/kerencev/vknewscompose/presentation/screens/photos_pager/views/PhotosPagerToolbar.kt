package com.kerencev.vknewscompose.presentation.screens.photos_pager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.BlackAlpha

@Composable
fun PhotosPagerToolbar(
    currentPage: Int,
    totalPages: Int,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlackAlpha)
    ) {
        Spacer(
            modifier = Modifier
                .height(WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
        )
        Row(
            modifier = Modifier
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                text = stringResource(
                    id = R.string.set_page, currentPage + 1, totalPages
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}