package pl.krystiankaniowski.performance.awake.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.awake.R
import pl.krystiankaniowski.performance.awake.usecase.IsKeepAwakeEnabledUseCase
import pl.krystiankaniowski.performance.awake.usecase.SetKeepAwakeEnabledUseCase
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import javax.inject.Inject

class AwakeSettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val isKeepAwakeEnabledUseCase: IsKeepAwakeEnabledUseCase,
    private val setKeepAwakeEnabledUseCase: SetKeepAwakeEnabledUseCase,
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
                    buildKeepAwakeEnabledItem(),
                ),
            )
        }
    }

    private suspend fun buildKeepAwakeEnabledItem() = SettingsItem.Switch(
        order = SettingsItems.Order.AWAKE_KEEP_AWAKE,
        category = SettingsItems.Category.OTHERS,
        title = stringsProvider.getString(R.string.awake_settings_keep_awake_title),
        description = null,
        value = isKeepAwakeEnabledUseCase(),
        isEnabled = true,
        onValueChanged = ::onKeepAwakeEnabledChanged,
    )

    private fun onKeepAwakeEnabledChanged(value: Boolean) = scope.launch {
        setKeepAwakeEnabledUseCase(value)
        emitItems()
    }
}