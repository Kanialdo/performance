package pl.krystiankaniowski.performance.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val onFocusStartUseCase: OnFocusStartUseCase,
    private val onFocusEndUseCase: OnFocusEndUseCase,
    private val getTimerUseCase: GetTimerUseCase,
) : ViewModel() {

    private val seconds = 25.toDuration(DurationUnit.SECONDS).inWholeSeconds

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            counter = seconds.toTextTime(),
            isTimerActive = false,
            isStartButtonEnabled = true,
            isStopButtonEnabled = false,
        ),
    )
    val state: StateFlow<State> = _state

    private var job: Job? = null

    init {
        viewModelScope.launch {
            getTimerUseCase().state.collect {
                
            }
        }
        observeTimer()
    }

    fun onEvent(event: Event) = when (event) {
        Event.Start -> onStart()
        Event.Stop -> onStop()
    }

    private fun onStart() {
        viewModelScope.launch {
            onFocusStartUseCase(Seconds(seconds))
            observeTimer()
        }
    }

    private fun onStop() {
        viewModelScope.launch {
            job?.cancel()
            onFocusEndUseCase()
        }
    }

    private fun observeTimer() {
        job = viewModelScope.launch {
            getFocusCounterUseCase()
                ?.onCompletion {
                    _state.value = State(
                        counter = seconds.toTextTime(),
                        isTimerActive = false,
                        isStartButtonEnabled = true,
                        isStopButtonEnabled = false,
                    )
                }
                ?.collect {
                    _state.value = State(
                        counter = it.value.toTextTime(),
                        isTimerActive = true,
                        isStartButtonEnabled = false,
                        isStopButtonEnabled = true,
                    )
                }
        }
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