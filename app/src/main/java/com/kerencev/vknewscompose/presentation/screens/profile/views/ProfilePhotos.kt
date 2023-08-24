package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.views.BoxIcon
import com.kerencev.vknewscompose.presentation.common.views.CardTitle
import com.kerencev.vknewscompose.presentation.common.views.ShimmerDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfilePhotos(
    modifier: Modifier = Modifier,
    photos: List<PhotoModel>,
    photosTotalCount: Int,
    isPhotosLoading: Boolean,
    photosErrorMessage: String?,
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
            if (photos.isNotEmpty()) {
                ProfilePhotosGrid {
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
                    when {
                        isPhotosLoading -> {
                            repeat(6) {
                                item { ShimmerDefault(modifier = Modifier.size(100.dp)) }
                            }
                        }

                        photosErrorMessage != null -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                BoxIcon(
                                    modifier = Modifier.width(100.dp),
                                    imageVector = Icons.Default.Refresh,
                                    onClick = loadPhotos
                                )
                            }
                        }

                        photos.size == photosTotalCount -> return@ProfilePhotosGrid
                        else -> item { SideEffect { loadPhotos() } }
                    }
                }
            } else {
                when {
                    isPhotosLoading -> ProfilePhotosGrid {
                        repeat(20) {
                            item { ShimmerDefault(modifier = Modifier.size(100.dp)) }
                        }
                    }

                    photosErrorMessage != null -> TextWithButton(
                        modifier = Modifier.height(220.dp),
                        title = stringResource(id = R.string.profile_photos_error),
                        onClick = loadPhotos
                    )

                    else -> SideEffect { loadPhotos() }
                }
            }
        }
    }
}