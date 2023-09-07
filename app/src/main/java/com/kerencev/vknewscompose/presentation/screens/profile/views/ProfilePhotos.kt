package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.Photo
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.views.icon.BoxIcon
import com.kerencev.vknewscompose.presentation.common.views.loading.ShimmerDefault
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfilePhotos(
    modifier: Modifier = Modifier,
    photos: List<Photo>,
    totalCount: Int,
    isLoading: Boolean,
    errorMessage: String?,
    onPhotoClick: (index: Int) -> Unit,
    onShowAllClick: () -> Unit,
    loadPhotos: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .clickable { onShowAllClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.photo),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = stringResource(id = R.string.show_all),
                    style = MaterialTheme.typography.body2
                )
                Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = LightBlue
                )
            }
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = getHorizontalArrangement(photos, errorMessage),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(photos) { index, item ->
                    if (item is PhotoModel) AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { onPhotoClick(index) },
                        model = item.url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    else ShimmerItems()
                }
                when {
                    photos.size == totalCount -> return@LazyHorizontalGrid
                    isLoading && photos.isEmpty() -> item { ShimmerItems() }

                    errorMessage != null -> item(span = { GridItemSpan(maxLineSpan) }) {
                        BoxIcon(
                            modifier = Modifier.width(100.dp),
                            imageVector = Icons.Default.Refresh,
                            onClick = loadPhotos
                        )
                    }

                    photos.isNotEmpty() && !isLoading -> item {
                        SideEffect { loadPhotos() }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerItems() {
    repeat(10) { ShimmerDefault(modifier = Modifier.size(100.dp)) }
}

private fun getHorizontalArrangement(photos: List<Photo>, photosErrorMessage: String?) =
    if (photosErrorMessage != null && photos.isEmpty())
        Arrangement.Center else Arrangement.spacedBy(2.dp)
