package pl.krystiankaniowski.performance.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.usecase.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.SetDoNotDisturbEnabledUseCase
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @Named("appVersion") private val applicationVersion: String,
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase,
    private val setDoNotDisturbEnabledUseCase: SetDoNotDisturbEnabledUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            _state.value = State.Loaded(
                appVersion = applicationVersion,
                isDndEnabled = isDoNotDisturbEnabledUseCase(),
            )
        }
    }

    fun onDndChanged(value: Boolean) = viewModelScope.launch {
        setDoNotDisturbEnabledUseCase(value)
        _state.value = (_state.value as State.Loaded).copy(
            isDndEnabled = isDoNotDisturbEnabledUseCase(),
        )
    }

    sealed interface State {
        object Loading : State
        data class Loaded(
            val appVersion: String,
            val isDndEnabled: Boolean,
        ) : State
    }
}