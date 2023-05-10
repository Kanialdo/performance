package pl.krystiankaniowski.performance.historyadd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.historyadd.HistoryAddEditArgs.id

class ViewModelState<T>(val scope: CoroutineScope, init: T) {

    private val _state = MutableStateFlow(init)
    val state: StateFlow<T> = _state

    val value: T get() = _state.value

    fun update(transform: suspend T.() -> T) = scope.launch {
        (_state.value as? T)?.let {
            _state.value = transform(it)
        }
    }

    fun <E : T> updateIf(transform: suspend E.() -> T) = scope.launch {
        (_state.value as? E)?.let {
            _state.value = transform(it)
        }
    }

    fun run(command: suspend T.() -> Unit) = scope.launch {
        (_state.value as? T)?.let {
            command(it)
        }
    }

    fun <E : T> runIf(command: suspend E.() -> Unit) = scope.launch {
        (_state.value as? E)?.let {
            command(it)
        }
    }
}

private val _state: MutableStateFlow<HistoryAddViewModel.State> = MutableStateFlow(if (id != null) HistoryAddViewModel.State.Loading else HistoryAddViewModel.State.Loaded())
val state: StateFlow<HistoryAddViewModel.State> = _state

inline fun <reified T> MutableStateFlow<in T>.updateIf(transform: T.() -> T) {
    (value as? T)?.let {
        value = transform(it)
    }
}

inline fun <reified T> MutableStateFlow<in T>.runIf(command: T.() -> Unit) {
    (value as? T)?.let {
        command(it)
    }
}

inline fun <reified T> MutableStateFlow<in T>.runIf(scope: CoroutineScope, noinline command: T.() -> Unit) = scope.launch {
    (value as? T)?.let {
        command(it)
    }
}