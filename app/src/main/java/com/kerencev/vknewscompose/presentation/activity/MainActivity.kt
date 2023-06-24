package com.kerencev.vknewscompose.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.screens.login.LoginScreen
import com.kerencev.vknewscompose.presentation.screens.main.MainScreen
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val component = getApplicationComponent()
            val viewModelFactory = component.getViewModelFactory()
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            val authState = viewModel.authState.collectAsState(AuthState.Initial)

            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                viewModel.performAuthResult()
            }

            VkNewsComposeTheme {
                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
                    }

                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    is AuthState.Initial -> Unit
                }
            }
        }
    }
}
