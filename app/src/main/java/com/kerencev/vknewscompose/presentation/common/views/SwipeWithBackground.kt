package com.kerencev.vknewscompose.presentation.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeWithBackground(
    modifier: Modifier = Modifier,
    dismissDirection: DismissDirection = DismissDirection.EndToStart,
    onDismiss: () -> Unit,
    backgroundColor: Color,
    backgroundText: String? = null,
    backgroundTextColor: Color = MaterialTheme.colors.onBackground,
    content: @Composable RowScope.() -> Unit
) {
    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(dismissDirection)) {
        onDismiss()
    }
    SwipeToDismiss(
        modifier = modifier,
        directions = setOf(dismissDirection),
        state = dismissState,
        background = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .alpha(0.5f)
                    .background(backgroundColor),
                contentAlignment = if (dismissDirection == DismissDirection.EndToStart)
                    Alignment.CenterEnd
                else Alignment.CenterStart,
            ) {
                backgroundText?.let {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = backgroundText,
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic,
                        color = backgroundTextColor
                    )
                }
            }
        }
    ) {
        content()
    }
}