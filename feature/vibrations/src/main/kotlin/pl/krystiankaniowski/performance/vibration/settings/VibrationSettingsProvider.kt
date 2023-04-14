package pl.krystiankaniowski.performance.vibration.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.vibration.usecase.IsVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibration.usecase.SetVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibrations.R
import javax.inject.Inject

class VibrationSettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val isVibrationEnabledUseCase: IsVibrationEnabledUseCase,
    private val setVibrationEnabledUseCase: SetVibrationEnabledUseCase,
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
                    buildIsVibrationEnabled(),
                ),
            )
        }
    }

    private suspend fun buildIsVibrationEnabled() = SettingsItem.Switch(
        order = SettingsItems.Order.SOUND_ENABLED,
        category = SettingsItems.Category.NOTIFICATIONS,
        title = stringsProvider.getString(R.string.setting_item_vibration_enabled_title),
        description = stringsProvider.getString(R.string.setting_item_vibration_enabled_description),
        value = isVibrationEnabledUseCase(),
        isEnabled = true,
        onValueChanged = { onVibrationChanged(it) },
    )

    private fun onVibrationChanged(value: Boolean) = scope.launch {
        setVibrationEnabledUseCase(value)
        emitItems()
    }
}