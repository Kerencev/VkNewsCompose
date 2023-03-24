package com.kerencev.vknewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsComposeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    NewsCard()
                }
            }
        }
    }
}

@Composable
fun NewsCard() {
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            NewsHeader()
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(id = R.string.temp_text))
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = stringResource(id = R.string.post_content_image),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsFooter()
        }
    }
}

@Composable
fun NewsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(
                id = R.string.group_avatar
            )
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text(
                text = "Название группы",
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "14:00",
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.more),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun NewsFooter() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(iconRes = R.drawable.ic_views_count, text = "976")
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(iconRes = R.drawable.ic_share, text = "7")
            IconWithText(iconRes = R.drawable.ic_comment, text = "8")
            IconWithText(iconRes = R.drawable.ic_like, text = "23")
        }
    }
}

@Composable
fun IconWithText(
    @DrawableRes iconRes: Int,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = MaterialTheme.colors.onSecondary)
    }
}

@Preview
@Composable
fun NewsCardPreviewLight() {
    VkNewsComposeTheme(darkTheme = false) {
        NewsCard()
    }
}

@Preview
@Composable
fun NewsCardPreviewNight() {
    VkNewsComposeTheme(darkTheme = true) {
        NewsCard()
    }
}