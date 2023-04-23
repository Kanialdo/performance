package pl.krystiankaniowski.performance.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelState<T>(private val scope: CoroutineScope, initState: T) {

    private val _state = MutableStateFlow(initState)
    val value: T get() = _state.value

    fun asStateFlow(): StateFlow<T> = _state

    fun updateNow(value: T) {
        _state.value = value
    }

    fun update(transform: suspend T.() -> T) = scope.launch {
        _state.value = transform(_state.value)
    }

    fun <E : T> updateIf(transform: suspend E.() -> T) = scope.launch {
        (_state.value as? E)?.let {
            _state.value = transform(it)
        }
    }

    fun run(command: suspend T.() -> Unit) = scope.launch {
        command(_state.value)
    }

    fun <E : T> runIf(command: suspend E.() -> Unit) = scope.launch {
        (_state.value as? E)?.let {
            command(it)
        }
    }
}