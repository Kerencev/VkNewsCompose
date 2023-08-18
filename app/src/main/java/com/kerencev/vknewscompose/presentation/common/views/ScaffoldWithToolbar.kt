package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithToolbar(
    title: String? = null,
    subTitle: String? = null,
    onBackPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                title = {
                    Column {
                        Text(
                            text = title.orEmpty(),
                            maxLines = 1,
                            fontSize = 20.sp
                        )
                        Text(
                            text = subTitle.orEmpty(),
                            maxLines = 1,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.surface,
                    scrolledContainerColor = MaterialTheme.colors.surface,
                    titleContentColor = MaterialTheme.colors.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        content(it)
    }
}