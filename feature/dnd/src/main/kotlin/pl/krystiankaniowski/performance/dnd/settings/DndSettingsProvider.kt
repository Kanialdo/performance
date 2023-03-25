package pl.krystiankaniowski.performance.dnd.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import javax.inject.Inject

class DndSettingsProvider @Inject constructor(
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase,
    private val setDoNotDisturbEnabledUseCase: SetDoNotDisturbEnabledUseCase,
) : SettingsItemsProvider {

    private val scope = MainScope()
    override val items: MutableStateFlow<List<SettingsItem>> = MutableStateFlow(emptyList())

    init {
        emitItems()
    }

    private fun emitItems() {
        scope.launch {
            items.emit(
                listOf(
                    SettingsItem.Switch(
                        order = 1,
                        title = "DND",
                        description = null,
                        value = isDoNotDisturbEnabledUseCase(),
                        isEnabled = true,
                        onValueChanged = { onDndChanged(it) },
                    ),
                ),
            )
        }
    }

    private fun onDndChanged(value: Boolean) = scope.launch {
        setDoNotDisturbEnabledUseCase(value)
        emitItems()
    }
}