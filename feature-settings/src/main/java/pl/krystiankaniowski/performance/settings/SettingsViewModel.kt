package pl.krystiankaniowski.performance.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @Named("appVersion") applicationVersion: String,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State(
            appVersion = applicationVersion,
        ),
    )
    val state: StateFlow<State> = _state

    data class State(
        val appVersion: String,
    )
}