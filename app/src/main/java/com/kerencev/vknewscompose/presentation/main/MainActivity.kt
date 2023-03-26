package com.kerencev.vknewscompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsComposeTheme {
                MainScreen()
            }
        }
    }
}
