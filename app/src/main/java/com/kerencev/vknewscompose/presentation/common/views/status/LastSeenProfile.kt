package com.kerencev.vknewscompose.presentation.common.views.status

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.domain.entities.LastSeen

@Composable
fun LastSeenProfile(
    modifier: Modifier = Modifier,
    lastSeen: LastSeen,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    color: Color = Color.White
) {
    Text(
        modifier = modifier,
        text = getLastSeenText(lastSeen = lastSeen),
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
private fun getLastSeenText(lastSeen: LastSeen): String {
    return if (lastSeen.days != null) stringResource(
        id = R.string.set_days, lastSeen.days
    ) else if (lastSeen.hours != null) stringResource(
        id = R.string.set_hours, lastSeen.hours
    ) else if (lastSeen.minutes > 0L) stringResource(
        id = R.string.set_minutes, lastSeen.minutes
    )
    else stringResource(id = R.string.now)
}