package pl.krystiankaniowski.performance.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun <T> MutableStateFlow<T>.update(value: T) {
    this.value = value
}

fun <T> MutableStateFlow<T>.transform(transform: T.() -> T) {
    value = transform(value)
}

fun <T> MutableStateFlow<T>.transform(scope: CoroutineScope, transform: suspend T.() -> T) = scope.launch {
    value = transform(value)
}

inline fun <reified T> MutableStateFlow<in T>.transformIf(noinline transform: T.() -> T) {
    if (value !is T) return
    value = transform(value as T)
}

inline fun <reified T> MutableStateFlow<in T>.transformIf(scope: CoroutineScope, noinline transform: suspend T.() -> T) = scope.launch {
    if (value !is T) return@launch
    value = transform(value as T)
}

fun <T> MutableStateFlow<T>.run(scope: CoroutineScope, command: suspend T.() -> Unit) = scope.launch {
    command((value))
}

inline fun <reified T> MutableStateFlow<in T>.runIf(noinline command: T.() -> Unit) {
    if (value !is T) return
    command((value as T))
}

inline fun <reified T> MutableStateFlow<in T>.runIf(scope: CoroutineScope, noinline command: suspend T.() -> Unit) = scope.launch {
    if (value !is T) return@launch
    command((value as T))
}
