package com.kerencev.vknewscompose.presentation.common.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<E : VkEvent, S : VkState, H : VkShot>(
    private val savedState: SavedStateHandle? = null,
) : ViewModel() {
    companion object {
        private const val KEY_STATE = "VK_STATE"
    }

    private val commandFlow = MutableSharedFlow<VkCommand>()
    private val shotFlow = MutableSharedFlow<H>(1)
    private val stateFlow = MutableStateFlow(this.initState())

    val observedShot = shotFlow.asSharedFlow()
    val observedState = stateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(0),
        savedState?.get(KEY_STATE) ?: this.initState()
    )

    protected val state = { observedState.value }

    init {
        observeState()
        observeShot()
        observeCommand()
    }

    /**
     * Save each snapshot in savedState, overwriting the previous State
     */
    private fun observeState() {
        stateFlow
            .onEach { savedState?.set(KEY_STATE, it) }
    }

    private fun observeShot() {
        shotFlow
            .launchIn(viewModelScope)
    }

    /**
     * Observe Command and, depending on the subtype, produce either State, or Feature, or Shot
     */
    private fun observeCommand() {
        commandFlow
            .onEach {
                when (it) {
                    is VkAction -> {
                        val feature = features(it)
                        if (feature != null) {
                            executeFeature(feature)
                        } else {
                            produceState(it)
                        }
                    }

                    is VkEffect -> produceShot(it)
                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun executeFeature(feature: Flow<VkCommand>) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                feature.onEach(commandFlow::emit).launchIn(this)
            }
        }
    }

    /**
     * Send the intent to the ViewModel and it falls into the produceCommand()
     */
    fun send(event: E) {
        viewModelScope.launch { commandFlow.emit(produceCommand(event)) }
    }

    protected suspend fun setShot(reduce: () -> H) = shotFlow.emit(reduce())

    protected suspend fun setState(reduce: S.() -> S) = stateFlow.emit(state().reduce())

    /**
     * Setting the initial State
     */
    protected abstract fun initState(): S

    protected abstract fun produceCommand(event: E): VkCommand

    /**
     * Have to take only OutputAction that will form the State
     */
    protected abstract suspend fun produceState(action: VkAction)

    /**
     * Have to take only Effect that will form the state Shot
     */
    protected abstract suspend fun produceShot(effect: VkEffect)

    /**
     * Have to take only InputAction depending on which the necessary Feature will be launched
     */
    protected abstract fun features(action: VkAction): Flow<VkCommand>?
}