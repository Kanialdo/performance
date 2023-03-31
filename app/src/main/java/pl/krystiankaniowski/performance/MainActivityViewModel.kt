package pl.krystiankaniowski.performance

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.awake.usecase.ObserveKeepAwakeEnabledUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val timer: PerformanceTimer,
    private val observeKeepAwakeEnabledUseCase: ObserveKeepAwakeEnabledUseCase,
) : ViewModel(), NavController.OnDestinationChangedListener {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            timer.state.collect {
                when (it) {
                    PerformanceTimer.State.NotStarted -> _state.value = _state.value.copy(isTimerActive = false)
                    is PerformanceTimer.State.Pending -> _state.value = _state.value.copy(isTimerActive = true)
                }
            }
        }
        viewModelScope.launch {
            observeKeepAwakeEnabledUseCase().collect {
                _state.value = _state.value.copy(isKeepAwakeEnabled = it)
            }
        }
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        _state.value = _state.value.copy(isTimerVisible = destination.route == "timer")
    }

    data class State(
        private val isTimerVisible: Boolean = true,
        private val isTimerActive: Boolean = false,
        private val isKeepAwakeEnabled: Boolean = false,
    ) {
        val keepAwake: Boolean = isTimerVisible && isTimerActive && isKeepAwakeEnabled
    }
}