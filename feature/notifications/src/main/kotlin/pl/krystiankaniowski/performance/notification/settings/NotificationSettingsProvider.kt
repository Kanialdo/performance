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
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

class NotificationSettingsProvider @Inject constructor(
    private val navigator: Navigator,
    private val stringsProvider: StringsProvider,
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
}