package pl.krystiankaniowski.performance.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.timer.GetStopTimerActionUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timer: PerformanceTimer,
    private val getStopTimerActionUseCase: GetStopTimerActionUseCase,
) : ViewModel() {

    private val seconds = 25.toDuration(DurationUnit.MINUTES).inWholeSeconds

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            counter = seconds.toTextTime(),
            isTimerActive = false,
            button = State.Button.Start,
        ),
    )
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            timer.state.collect { timerState ->
                _state.value = when (timerState) {
                    PerformanceTimer.State.NotStarted -> State(
                        counter = seconds.toTextTime(),
                        isTimerActive = false,
                        button = State.Button.Start,
                    )
                    is PerformanceTimer.State.Pending -> State(
                        counter = timerState.leftSeconds.value.toTextTime(),
                        isTimerActive = true,
                        button = when (val action = getStopTimerActionUseCase()) {
                            is GetStopTimerActionUseCase.Action.Cancel -> State.Button.Cancel(action.secondsLeft.value.toInt())
                            GetStopTimerActionUseCase.Action.GiveUp -> State.Button.Stop
                        },
                    )
                }
            }
        }
    }

    private fun onButtonClicked() {
        when (state.value.button) {
            is State.Button.Cancel -> timer.cancel()
            State.Button.Start -> onStart()
            State.Button.Stop -> onStop()
        }
    }

    fun onEvent(event: Event) = when (event) {
        Event.Start -> onStart()
        Event.Stop -> onStop()
        Event.OnButtonClick -> onButtonClicked()
    }

    private fun onStart() {
        viewModelScope.launch {
            timer.start(Seconds(seconds))
        }
    }

    private fun onStop() {
        viewModelScope.launch {
            timer.stop()
        }
    }

    private fun Long.toTextTime() = "${this / 60}:${(this % 60).toString().padStart(2, '0')}"

    data class State(
        val counter: String,
        val isTimerActive: Boolean,
        val button: Button,
    ) {

        sealed interface Button {
            object Start : Button
            object Stop : Button
            data class Cancel(val secondsLeft: Int) : Button
        }
    }

    sealed class Event {
        object Start : Event()
        object Stop : Event()
        object OnButtonClick : Event()
    }

    sealed class Effect
}