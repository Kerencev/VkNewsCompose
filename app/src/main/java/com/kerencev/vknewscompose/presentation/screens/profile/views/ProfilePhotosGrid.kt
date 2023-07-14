package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.views.CardTitle
import com.kerencev.vknewscompose.ui.theme.LightBlue

@Composable
fun ProfilePhotosGrid(
    photos: List<PhotoModel>,
    onPhotoClick: (Int) -> Unit,
    onShowAllClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .clickable { onShowAllClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardTitle(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.photo)
            )
            CardTitle(text = stringResource(id = R.string.show_all))
            Icon(
                modifier = Modifier.padding(top = 2.dp),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = LightBlue
            )
        }
        LazyHorizontalGrid(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            itemsIndexed(photos) { index, item ->
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onPhotoClick(index) },
                    model = item.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}