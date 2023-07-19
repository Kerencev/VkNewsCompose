package com.kerencev.vknewscompose.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.common.compose.SetupStatusColors
import com.kerencev.vknewscompose.presentation.navigation.AppNavGraph
import com.kerencev.vknewscompose.presentation.navigation.Screen
import com.kerencev.vknewscompose.presentation.screens.login.LoginScreen
import com.kerencev.vknewscompose.presentation.screens.main.MainScreen
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainEvent
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState
import com.kerencev.vknewscompose.presentation.screens.profile_photos_pager.ProfilePhotosPagerScreen
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val component = getApplicationComponent()
            val viewModelFactory = component.getViewModelFactory()
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            val state = viewModel.observedState.collectAsState(MainState())

            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                viewModel.send(MainEvent.CheckAuthState)
            }

            VkNewsComposeTheme {
                SetupStatusColors(
                    color = MaterialTheme.colors.surface,
                    isAppearanceLightStatusBars = !isSystemInDarkTheme()
                )

                when (state.value.authState) {
                    AuthState.INITIAL -> Unit

                    AuthState.AUTHORIZED -> {
                        AppNavGraph(
                            navHostController = navController,
                            mainScreenContent = {
                                MainScreen(
                                    mainViewModel = viewModel,
                                    viewModelFactory = viewModelFactory,
                                    onPhotoClick = { number ->
                                        navController.navigate(
                                            Screen.PhotosPager.getRouteWithArgs(
                                                initialNumber = number
                                            )
                                        )
                                    }
                                )
                            },
                            photosSliderScreenContent = { number ->
                                ProfilePhotosPagerScreen(
                                    viewModelFactory = viewModelFactory,
                                    selectedPhotoNumber = number,
                                    onDismiss = { navController.popBackStack() }
                                )
                            }
                        )
                    }

                    AuthState.NOT_AUTHORIZED -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS, VKScope.PHOTOS))
                        }
                    }

                    AuthState.LOG_OUT -> {
                        navController.graph.clear()
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS, VKScope.PHOTOS))
                        }
                    }
                }
            }
        }
    }
}
