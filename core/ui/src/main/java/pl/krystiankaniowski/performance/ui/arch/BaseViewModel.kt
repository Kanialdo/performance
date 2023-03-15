package pl.krystiankaniowski.performance.ui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Effect> : ViewModel() {

    protected val _state: MutableStateFlow<State> = MutableStateFlow(initState())
    val state: StateFlow<State> = _state

    protected val _effects: Channel<Effect> = Channel()
    val effects: Flow<Effect> = _effects.receiveAsFlow()

    protected abstract fun initState(): State

    protected fun updateState(handler: suspend (State) -> State?) {
        viewModelScope.launch {
            handler(_state.value)?.let { _state.value = it }
        }
    }
}