package pl.krystiankaniowski.performance.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.architecture.update
import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.domain.timer.fits
import pl.krystiankaniowski.performance.domain.timer.left
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.model.toSeconds
import pl.krystiankaniowski.performance.timer.usecase.GetProgressUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timer: PerformanceTimer,
    private val timerFormatter: TimerFormatter,
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase,
    private val getProgressUseCase: GetProgressUseCase,
) : ViewModel() {

    private val seconds = 25.minutes.inWholeSeconds.toSeconds()

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            counter = timerFormatter.format(seconds),
            isTimerActive = false,
            button = State.Button.Start,
            progress = 0f,
        ),
    )
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            timer.state.collect { timerState ->
                _state.update(
                    when (timerState) {
                        PerformanceTimer.State.NotStarted -> State(
                            counter = timerFormatter.format(seconds),
                            isTimerActive = false,
                            button = State.Button.Start,
                            progress = 0f,
                        )
                        is PerformanceTimer.State.Pending -> State(
                            counter = timerFormatter.format(timerState.leftSeconds),
                            isTimerActive = true,
                            button = if (getCancelThresholdUseCase.fits(timerState)) {
                                State.Button.Cancel(getCancelThresholdUseCase.left(timerState))
                            } else {
                                State.Button.Stop
                            },
                            progress = getProgressUseCase(timerState),
                        )
                    },
                )
            }
        }
    }

    fun onEvent(event: Event) = when (event) {
        Event.Start -> timer.start(seconds)
        Event.Stop -> timer.stop()
        Event.Cancel -> timer.stop()
    }

    data class State(
        val counter: String,
        val isTimerActive: Boolean,
        val button: Button,
        val progress: Float,
    ) {

        sealed interface Button {
            object Start : Button
            object Stop : Button
            data class Cancel(val secondsLeft: Seconds) : Button
        }
    }

    sealed class Event {
        object Start : Event()
        object Stop : Event()
        object Cancel : Event()
    }

    sealed class Effect
}