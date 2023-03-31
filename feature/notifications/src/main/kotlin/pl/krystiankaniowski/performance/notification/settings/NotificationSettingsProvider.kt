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
import pl.krystiankaniowski.performance.notification.usecase.IsShowTimeEnabledUseCase
import pl.krystiankaniowski.performance.notification.usecase.SetShowTimeEnabledUseCase
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

class NotificationSettingsProvider @Inject constructor(
    private val navigator: Navigator,
    private val stringsProvider: StringsProvider,
    private val isShowTimeEnabledUseCase: IsShowTimeEnabledUseCase,
    private val setShowTimeEnabledUseCase: SetShowTimeEnabledUseCase,
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
                    buildAndroidAppSettings(),
                    buildShowTimeEnabled(),
                ),
            )
        }
    }

    private fun buildAndroidAppSettings() = SettingsItem.Simple(
        order = SettingsOrder.NOTIFICATION_ANDROID_APP_SETTINGS,
        title = stringsProvider.getString(R.string.app_notifications_settings),
        description = null,
        onClick = { navigator.open(Destination.Android.AppNotificationsSettings) },
    )

    private suspend fun buildShowTimeEnabled() = SettingsItem.Switch(
        order = SettingsOrder.NOTIFICATION_SHOW_TIME_ENABLED,
        title = stringsProvider.getString(R.string.notfication_setting_show_time_title),
        description = null,
        value = isShowTimeEnabledUseCase(),
        isEnabled = true,
        onValueChanged = ::onShowTimeEnabledChanged,
    )

    private fun onShowTimeEnabledChanged(value: Boolean) = scope.launch {
        setShowTimeEnabledUseCase(value)
        emitItems()
    }
}