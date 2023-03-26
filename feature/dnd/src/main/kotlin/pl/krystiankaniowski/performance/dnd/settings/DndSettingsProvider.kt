package pl.krystiankaniowski.performance.dnd.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.dnd.R
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsOrder
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import javax.inject.Inject

class DndSettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
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
                    buildIsDndEnabled(),
                ),
            )
        }
    }

    private suspend fun buildIsDndEnabled() = SettingsItem.Switch(
        order = SettingsOrder.DND_ENABLED,
        title = stringsProvider.getString(R.string.do_not_disturbed),
        description = stringsProvider.getString(R.string.turn_on_do_not_disturbed_in_focus_time),
        value = isDoNotDisturbEnabledUseCase(),
        isEnabled = true,
        onValueChanged = { onDndChanged(it) },
    )

    private fun onDndChanged(value: Boolean) = scope.launch {
        setDoNotDisturbEnabledUseCase(value)
        emitItems()
    }
}