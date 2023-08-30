package com.kerencev.vknewscompose.presentation.screens.profile.views.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.Profile
import com.kerencev.vknewscompose.presentation.common.ContentState
import com.kerencev.vknewscompose.presentation.common.views.IconBack
import com.kerencev.vknewscompose.presentation.screens.profile.flow.ProfileViewState
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileToolbar(
    state: ProfileViewState,
    boxScope: BoxScope,
    onRefreshClick: () -> Unit,
    onBackPressed: () -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    val modifier = getDefaultModifier(state)

    boxScope.run {
        Column(
            modifier = if (state.profileState is ContentState.Loading)
                modifier.shimmer() else modifier
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
                AsyncImage(
                    modifier = Modifier
                        .padding(start = if (state.isCurrentUser == true) 8.dp else 52.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colors.surface, CircleShape),
                    model = getAvatarByState(state.profileState),
                    contentDescription = stringResource(id = R.string.user_avatar),
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    text = getToolbarTitle(state = state.profileState),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onPrimary
                )

            }
        }

        if (state.isCurrentUser == true) {
            Box(
                modifier = Modifier
                    .padding(
                        top = 8.dp + WindowInsets.systemBars
                            .asPaddingValues()
                            .calculateTopPadding(),
                        end = 8.dp,
                        start = 8.dp,
                        bottom = 8.dp
                    )
                    .alpha(state.blurBackgroundAlpha)
                    .size(32.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .background(MaterialTheme.colors.surface)
            )

            ProfileMenu(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = WindowInsets.systemBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    ),
                onRefreshClick = onRefreshClick,
                onLogoutClick = onLogoutClick,
            )
        } else {
            IconBack(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = WindowInsets.systemBars
                            .asPaddingValues()
                            .calculateTopPadding() + 12.dp
                    )
                    .clip(CircleShape)
                    .align(Alignment.TopStart)
                    .size(32.dp)
                    .background(MaterialTheme.colors.surface),
                onBackPressed = onBackPressed
            )
        }
    }
}

@Composable
private fun getToolbarTitle(state: ContentState<Profile>) = when (state) {
    is ContentState.Content -> state.data.name
    is ContentState.Loading -> ""
    is ContentState.Error -> stringResource(id = R.string.empty_data)
}

@Composable
private fun getDefaultModifier(state: ProfileViewState) = Modifier
    .alpha(state.topBarAlpha)
    .background(MaterialTheme.colors.surface)
    .fillMaxWidth()

private fun getAvatarByState(state: ContentState<Profile>) =
    if (state is ContentState.Content) state.data.avatarUrl else R.drawable.ic_people
