package com.kerencev.vknewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                NewsCard()
            }
        }
    }
}

@Composable
fun NewsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
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