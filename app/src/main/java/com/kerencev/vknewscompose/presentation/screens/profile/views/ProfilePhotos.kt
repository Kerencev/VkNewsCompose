package com.kerencev.vknewscompose.presentation.screens.profile.views

import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.PhotoModel
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.views.ShimmerDefault
import com.kerencev.vknewscompose.presentation.common.views.TextWithButton
import com.kerencev.vknewscompose.ui.theme.Shapes

@Composable
fun ProfilePhotos(
    modifier: Modifier = Modifier,
    photosState: ContentState<List<PhotoModel>>,
    onPhotoClick: (index: Int) -> Unit,
    onShowAllClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
    ) {
        when (photosState) {
            is ContentState.Content -> ProfilePhotosGrid(
                photos = photosState.data,
                onPhotoClick = onPhotoClick,
                onShowAllClick = onShowAllClick
            )

            is ContentState.Loading -> ShimmerDefault(modifier = Modifier.height(220.dp))
            is ContentState.Error -> TextWithButton(
                modifier = Modifier.height(220.dp),
                title = stringResource(id = R.string.profile_photos_error),
                onClick = onRetryClick
            )
        }
    }
}