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

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Preparing)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            timer.state.collect { timerState ->
                _state.value = when (timerState) {
                    PerformanceTimer.State.NotStarted -> State.Loaded(
                        counter = seconds.toTextTime(),
                        isTimerActive = false,
                        buttonLabel = "Start",
                    )

                    is PerformanceTimer.State.Pending -> State.Loaded(
                        counter = timerState.leftSeconds.value.toTextTime(),
                        isTimerActive = true,
                        buttonLabel = when (val action = getStopTimerActionUseCase()) {
                            is GetStopTimerActionUseCase.Action.Cancel -> "Cancel (${action.secondsLeft})"
                            GetStopTimerActionUseCase.Action.GiveUp -> "Give up"
                        },
                    )
                }
            }
        }
    }

    fun onButtonClick() {
        state.value.let { it as? State.Loaded }?.let {
            if (it.isTimerActive) {
                onStop()
            } else {
                onStart()
            }
        }
    }

    fun onEvent(event: Event) = when (event) {
        Event.Start -> onStart()
        Event.Stop -> onStop()
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

    sealed interface State {
        object Preparing : State
        data class Loaded(
            val counter: String,
            val isTimerActive: Boolean,
            val buttonLabel: String,
        ) : State
    }

    sealed class Event {
        object Start : Event()
        object Stop : Event()
    }

    sealed class Effect
}