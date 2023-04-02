package pl.krystiankaniowski.performance.domain.settings

object SettingsOrder {

    private var autoOrder: Int = 0
        get() {
            field += 1
            return field
        }

    val DND_ENABLED = autoOrder
    val DND_ANDROID_SETTINGS = autoOrder
    val NOTIFICATION_ANDROID_APP_SETTINGS = autoOrder
    val NOTIFICATION_SHOW_TIME_ENABLED = autoOrder
    val AWAKE_KEEP_AWAKE = autoOrder
    val STATS_DEV_GENERATE_HISTORY = autoOrder
    val STATS_REMOVE_HISTORY = autoOrder
    val APP_VERSION = autoOrder
}