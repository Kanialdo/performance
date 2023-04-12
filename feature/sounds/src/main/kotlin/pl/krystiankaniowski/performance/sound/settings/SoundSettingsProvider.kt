package pl.krystiankaniowski.performance.sound.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.sound.R
import pl.krystiankaniowski.performance.sound.usecase.IsSoundEnabledUseCase
import pl.krystiankaniowski.performance.sound.usecase.SetSoundEnabledUseCase
import javax.inject.Inject

class SoundSettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val isSoundEnabledUseCase: IsSoundEnabledUseCase,
    private val setSoundEnabledUseCase: SetSoundEnabledUseCase,
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
                    buildIsSoundEnabled(),
                ),
            )
        }
    }

    private suspend fun buildIsSoundEnabled() = SettingsItem.Switch(
        order = SettingsItems.Order.SOUND_ENABLED,
        category = SettingsItems.Category.NOTIFICATIONS,
        title = stringsProvider.getString(R.string.setting_item_sound_enabled_title),
        description = stringsProvider.getString(R.string.setting_item_sound_enabled_description),
        value = isSoundEnabledUseCase(),
        isEnabled = true,
        onValueChanged = { onSoundChanged(it) },
    )

    private fun onSoundChanged(value: Boolean) = scope.launch {
        setSoundEnabledUseCase(value)
        emitItems()
    }
}