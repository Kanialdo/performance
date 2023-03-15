package pl.krystiankaniowski.performance.timer

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.ui.arch.BaseViewModel
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val saveFocusUseCases: SaveFocusUseCase,
) : BaseViewModel<TimerViewModel.State, Nothing>() {

    private val seconds = 25.toDuration(DurationUnit.MINUTES).inWholeSeconds

    override fun initState() = State(
        counter = seconds.toTextTime(),
        isTimerActive = false,
        isStartButtonEnabled = true,
        isStopButtonEnabled = false,
    )

    private var job: Job? = null
    private var dateStart: Instant? = null

    fun onEvent(event: Event) = when (event) {
        Event.Start -> onStart()
        Event.Stop -> onStop()
    }

    private fun onStart() {
        dateStart = Clock.System.now()
        job = viewModelScope.launch {
            (seconds - 1 downTo 0)
                .asFlow() // Emit total - 1 because the first was emitted onStart
                .onEach { delay(1000) } // Each second later emit a number
                .onStart { emit(seconds) } // Emit total seconds immediately
                .onCompletion {
                    _state.value = State(
                        counter = seconds.toTextTime(),
                        isTimerActive = false,
                        isStartButtonEnabled = true,
                        isStopButtonEnabled = false,
                    )
                    viewModelScope.launch {
                        saveFocusUseCases(Focus(requireNotNull(dateStart), Clock.System.now()))
                    }
                }
                .conflate() // In case the creating of State takes some time, conflate keeps the time ticking separately
                .collect {
                    _state.value = State(
                        counter = it.toTextTime(),
                        isTimerActive = true,
                        isStartButtonEnabled = false,
                        isStopButtonEnabled = true,
                    )
                }
        }
    }

    private fun onStop() {
        job?.cancel()
    }

    private fun Long.toTextTime() = "${this / 60}:${(this % 60).toString().padStart(2, '0')}"

    data class State(
        val counter: String,
        val isTimerActive: Boolean,
        val isStartButtonEnabled: Boolean,
        val isStopButtonEnabled: Boolean,
    )

    sealed class Event {
        object Start : Event()
        object Stop : Event()
    }

    sealed class Effect
}