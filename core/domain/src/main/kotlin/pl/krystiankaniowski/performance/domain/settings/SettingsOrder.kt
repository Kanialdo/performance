package pl.krystiankaniowski.performance.domain.settings

object SettingsOrder {

    private var autoOrder: Int = 0
        get() {
            field += 1
            return field
        }

    val DND_ENABLED = autoOrder
    val DND_ANDROID_SETTINGS = autoOrder
    val APP_NOTIFICATIONS_SETTINGS = autoOrder
    val IS_TIME_IN_NOTIFICATION_ENABLED = autoOrder
    val REMOVE_ALL_DATA = autoOrder
    val APP_VERSION = autoOrder
}