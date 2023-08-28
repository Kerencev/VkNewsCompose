package com.kerencev.vknewscompose.presentation.screens.news.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.presentation.common.views.CardTitle

@Composable
fun NewsText(
    text: String,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colors.surface
    var isTextOverflow by rememberSaveable { mutableStateOf(false) }
    var isTextExpanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = if (isTextOverflow) modifier.clickable { isTextExpanded = !isTextExpanded }
        else modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            maxLines = if (isTextExpanded) Int.MAX_VALUE else 3,
            onTextLayout = { textLayoutResult ->
                isTextOverflow = textLayoutResult.hasVisualOverflow
            }
        )
        if (isTextOverflow) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp)
                        .drawWithContent {
                            val gradient = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, surfaceColor),
                                startX = 0f,
                                endX = size.width
                            )
                            drawContent()
                            drawRect(
                                brush = gradient,
                                alpha = 1f
                            )
                        }
                )
                Spacer(
                    modifier = Modifier
                        .background(color = surfaceColor)
                        .height(20.dp)
                        .width(2.dp)
                )
                CardTitle(
                    text = stringResource(id = R.string.show_more),
                    modifier = Modifier.background(color = surfaceColor)
                )
            }
        }
    }
}