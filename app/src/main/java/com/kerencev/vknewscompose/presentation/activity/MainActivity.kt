package com.kerencev.vknewscompose.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kerencev.vknewscompose.di.getApplicationComponent
import com.kerencev.vknewscompose.domain.entities.AuthState
import com.kerencev.vknewscompose.presentation.common.compose.SetupSystemBar
import com.kerencev.vknewscompose.presentation.navigation.AppNavGraph
import com.kerencev.vknewscompose.presentation.navigation.Screen
import com.kerencev.vknewscompose.presentation.screens.login.LoginScreen
import com.kerencev.vknewscompose.presentation.screens.main.MainScreen
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainEvent
import com.kerencev.vknewscompose.presentation.screens.main.flow.MainState
import com.kerencev.vknewscompose.presentation.screens.photos_pager.PhotosPagerScreen
import com.kerencev.vknewscompose.ui.theme.VkNewsComposeTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    private val vkAccessRights = listOf(VKScope.WALL, VKScope.FRIENDS, VKScope.PHOTOS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()

            val component = getApplicationComponent()
            val viewModelFactory = component.getViewModelFactory()
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            val state = viewModel.observedState.collectAsState(MainState())

            val launcher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                viewModel.send(MainEvent.CheckAuthState)
            }

            VkNewsComposeTheme {
                SetupSystemBar()
                when (state.value.authState) {
                    AuthState.INITIAL -> Unit

                    AuthState.AUTHORIZED -> {
                        AppNavGraph(
                            navHostController = navController,
                            mainScreenContent = {
                                MainScreen(
                                    mainViewModel = viewModel,
                                    viewModelFactory = viewModelFactory,
                                    onPhotoClick = { userId, type, index, newsModelId ->
                                        navController.navigate(
                                            Screen.PhotosPager.getRouteWithArgs(
                                                userId = userId,
                                                type = type,
                                                initialNumber = index,
                                                newsModelId = newsModelId
                                            )
                                        )
                                    }
                                )
                            },
                            photosPagerScreenContent = { params ->
                                PhotosPagerScreen(
                                    params = params,
                                    onDismiss = { navController.popBackStack() },
                                )
                            }
                        )
                    }

                    AuthState.NOT_AUTHORIZED -> {
                        LoginScreen { launcher.launch(vkAccessRights) }
                    }

                    AuthState.LOG_OUT -> {
                        navController.graph.clear()
                        LoginScreen { launcher.launch(vkAccessRights) }
                    }
                }
            }
        }
    }
}
