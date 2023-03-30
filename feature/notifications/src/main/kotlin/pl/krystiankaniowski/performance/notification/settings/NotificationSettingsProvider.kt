package pl.krystiankaniowski.performance.notification.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.navigation.Destination
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsOrder
import pl.krystiankaniowski.performance.notification.usecase.IsTimeInNotificationEnabledUseCase
import pl.krystiankaniowski.performance.notification.usecase.SetTimeInNotificationEnabledUseCase
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

class NotificationSettingsProvider @Inject constructor(
    private val navigator: Navigator,
    private val stringsProvider: StringsProvider,
    private val isTimeInNotificationEnabledUseCase: IsTimeInNotificationEnabledUseCase,
    private val setTimeInNotificationEnabledUseCase: SetTimeInNotificationEnabledUseCase,
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
                    buildOpenNotificationSettingsScreen(),
                    buildIsTimeInNotificationEnabled(),
                ),
            )
        }
    }

    private fun buildOpenNotificationSettingsScreen() = SettingsItem.Simple(
        order = SettingsOrder.APP_NOTIFICATIONS_SETTINGS,
        title = stringsProvider.getString(R.string.app_notifications_settings),
        description = null,
        onClick = { navigator.open(Destination.Android.AppNotificationsSettings) },
    )

    private suspend fun buildIsTimeInNotificationEnabled() = SettingsItem.Switch(
        order = SettingsOrder.IS_TIME_IN_NOTIFICATION_ENABLED,
        title = "IsTimeInNotificationEnabled",
        description = null,
        value = isTimeInNotificationEnabledUseCase(),
        isEnabled = true,
        onValueChanged = { onIsTimeInNotificationEnabled(it) },
    )

    private fun onIsTimeInNotificationEnabled(value: Boolean) = scope.launch {
        setTimeInNotificationEnabledUseCase(value)
        emitItems()
    }
}