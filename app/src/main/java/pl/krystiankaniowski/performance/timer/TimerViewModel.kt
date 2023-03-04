package pl.krystiankaniowski.performance.timer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            counter = "-",
            isTimerActive = false,
            isStartButtonEnabled = true,
        ),
    )
    val state: StateFlow<State> = _state

    fun onEvent(event: Event) = when (event) {
        Event.Start -> onStart()
    }

    private fun onStart() {
        _state.value = State(
            counter = "25:00",
            isTimerActive = true,
            isStartButtonEnabled = false,
        )
    }

    sealed class Event {
        object Start : Event()
    }

    data class State(
        val counter: String,
        val isTimerActive: Boolean,
        val isStartButtonEnabled: Boolean,
    )
}