package pl.krystiankaniowski.performance.timer

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.domain.timer.fits
import pl.krystiankaniowski.performance.domain.timer.left
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.model.toSeconds
import pl.krystiankaniowski.performance.ui.components.UiTag
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timer: PerformanceTimer,
    private val timerFormatter: TimerFormatter,
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase,
) : ViewModel() {

    private val seconds = 25.minutes.inWholeSeconds.toSeconds()

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            counter = timerFormatter.format(seconds),
            isTimerActive = false,
            button = State.Button.Start,
            tag = UiTag("Unknown", Color.Gray),
        ),
    )
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            timer.state.collect { timerState ->
                _state.value = when (timerState) {
                    PerformanceTimer.State.NotStarted -> _state.value.copy(
                        counter = timerFormatter.format(seconds),
                        isTimerActive = false,
                        button = State.Button.Start,
                    )

                    is PerformanceTimer.State.Pending -> _state.value.copy(
                        counter = timerFormatter.format(timerState.leftSeconds),
                        isTimerActive = true,
                        button = if (getCancelThresholdUseCase.fits(timerState)) {
                            State.Button.Cancel(getCancelThresholdUseCase.left(timerState))
                        } else {
                            State.Button.Stop
                        },
                    )
                }
            }
        }
    }

    fun onEvent(event: Event) = when (event) {
        Event.Start -> timer.start(state.value.tag, seconds)
        Event.Stop -> timer.stop()
        Event.Cancel -> timer.stop()
        Event.OnTagClick -> onTagClick()
        is Event.OnTagChanged -> onTagChanged(event.tag)
    }

    private fun onTagClick() = viewModelScope.launch {
        val tags = emptyList<State.ViewTag>() // TODO: Load
        _effects.emit(Effect.OpenTagSelector(tags = tags))
    }

    private fun onTagChanged(tag: State.ViewTag) {
        _state.value = _state.value.copy(tag = tag)
    }

    data class State(
        val counter: String,
        val isTimerActive: Boolean,
        val button: Button,
        val tag: UiTag,
    ) {

        sealed interface Button {
            object Start : Button
            object Stop : Button
            data class Cancel(val secondsLeft: Seconds) : Button
        }

        data class ViewTag(
            val name: String,
        )
    }

    sealed class Event {
        object Start : Event()
        object Stop : Event()
        object Cancel : Event()
        object OnTagClick : Event()
        data class OnTagChanged(val tag: State.ViewTag) : Event()
    }

    sealed interface Effect {
        data class OpenTagSelector(val tags: List<State.ViewTag>) : Effect
    }
}