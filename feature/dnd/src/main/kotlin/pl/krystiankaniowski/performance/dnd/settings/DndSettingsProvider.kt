package pl.krystiankaniowski.performance.dnd.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.dnd.R
import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.dnd.usecase.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.navigation.Destination
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsOrder
import javax.inject.Inject

class DndSettingsProvider @Inject constructor(
    private val navigator: Navigator,
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
                    buildOpenNotificationAccessScreen(),
                ),
            )
        }
    }

    private suspend fun buildIsDndEnabled() = SettingsItem.Switch(
        order = SettingsOrder.DND_ENABLED,
        category = SettingsOrder.Category.DND,
        title = stringsProvider.getString(R.string.do_not_disturbed),
        description = stringsProvider.getString(R.string.turn_on_do_not_disturbed_in_focus_time),
        value = isDoNotDisturbEnabledUseCase(),
        isEnabled = true,
        onValueChanged = { onDndChanged(it) },
    )

    private suspend fun buildOpenNotificationAccessScreen() = SettingsItem.Simple(
        order = SettingsOrder.DND_ANDROID_SETTINGS,
        category = SettingsOrder.Category.DND,
        title = stringsProvider.getString(R.string.do_not_disturbed_android_settings),
        description = null,
        onClick = { navigator.open(Destination.Android.NotificationPolicyAccessSettings) },
    )

    private fun onDndChanged(value: Boolean) = scope.launch {
        setDoNotDisturbEnabledUseCase(value)
        emitItems()
    }
}