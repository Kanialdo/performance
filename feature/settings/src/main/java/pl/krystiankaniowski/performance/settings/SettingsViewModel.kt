package pl.krystiankaniowski.performance.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val providers: Set<@JvmSuppressWildcards SettingsItemsProvider>,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            combine(flows = providers.map { it.items }, transform = { it }).collect { items ->
                _state.value = State.Loaded(
                    items = items.toList().flatten().sortedBy { it.order },
                )
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Loaded(
            val items: List<SettingsItem>,
        ) : State
    }
}