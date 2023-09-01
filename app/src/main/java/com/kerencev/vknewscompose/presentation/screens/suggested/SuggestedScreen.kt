package com.kerencev.vknewscompose.presentation.screens.suggested

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kerencev.vknewscompose.di.ViewModelFactory
import com.kerencev.vknewscompose.presentation.common.compose.rememberUnitParams
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedEvent
import com.kerencev.vknewscompose.presentation.screens.suggested.flow.SuggestedViewState

@Composable
fun SuggestedScreen(
    viewModelFactory: ViewModelFactory
) {
    val viewModel: SuggestedViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.observedState.collectAsState()
    val sendEvent: (SuggestedEvent) -> Unit = rememberUnitParams { viewModel.send(it) }
}

@Composable
fun SuggestedScreenContent(
    state: State<SuggestedViewState>
) {

}