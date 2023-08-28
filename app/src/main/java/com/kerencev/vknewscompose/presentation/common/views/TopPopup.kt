package com.kerencev.vknewscompose.presentation.common.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kerencev.vknewscompose.R
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

@Composable
fun TopPopupCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    iconTint: Color = Color.White,
    text: String,
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(50),
    backgroundColor: Color = LightBlue,
    onClick: (() -> Unit)? = null
) {
    val cardModifier = modifier.clip(shape)
    Card(
        modifier = onClick?.let { cardModifier.clickable(onClick = onClick) } ?: cardModifier,
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconRes?.let {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = iconTint
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = text,
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopPopupPreview() {
    VkNewsComposeTheme {
        TopPopupCard(
            modifier = Modifier.padding(16.dp),
            iconRes = R.drawable.ic_arrow_up,
            text = "Свежие записи",
            onClick = {}
        )
    }
}