package com.kerencev.vknewscompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsComposeTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
