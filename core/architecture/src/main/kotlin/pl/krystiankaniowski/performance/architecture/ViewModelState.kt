package pl.krystiankaniowski.performance.architecture

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelState<T>(initState: T) {

    private val _state = MutableStateFlow(initState)
    val value: T get() = _state.value

    fun asStateFlow(): StateFlow<T> = _state

    fun update(value: T) {
        _state.value = value
    }

    fun update(transform: T.() -> T) {
        _state.value = transform(_state.value)
    }

    inline fun <reified E : T> updateIf(noinline transform: E.() -> T) {
        if (value !is E) return
        update(transform(value as E))
    }

    fun run(command: T.() -> Unit) {
        command(_state.value)
    }

    inline fun <reified E : T> runIf(noinline command: E.() -> Unit) {
        if (value !is E) return
        command((value as E))
    }
}

inline fun <reified E : T, T> MutableStateFlow<T>.runIf(noinline command: E.() -> Unit) {
    if (value !is E) return
    command((value as E))
}
