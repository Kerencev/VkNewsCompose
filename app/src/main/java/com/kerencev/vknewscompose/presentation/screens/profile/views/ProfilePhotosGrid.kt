package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfilePhotosGrid(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(2.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(2.dp),
    content: LazyGridScope.() -> Unit
) {
    LazyHorizontalGrid(
        modifier = modifier
            .height(200.dp)
            .padding(8.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}