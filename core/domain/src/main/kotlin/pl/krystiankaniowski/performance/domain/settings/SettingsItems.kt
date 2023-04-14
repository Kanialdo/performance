package pl.krystiankaniowski.performance.domain.settings

sealed interface SettingsItem {

    val category: SettingsItems.Category
    val order: Int
    val title: String
    val description: String?

    data class Simple(
        override val order: Int,
        override val category: SettingsItems.Category,
        override val title: String,
        override val description: String? = null,
        val onClick: (() -> Unit)? = null,
    ) : SettingsItem

    data class Switch(
        override val order: Int,
        override val category: SettingsItems.Category,
        override val title: String,
        override val description: String? = null,
        val value: Boolean,
        val isEnabled: Boolean,
        val onValueChanged: (Boolean) -> Unit,
    ) : SettingsItem
}

object SettingsItems {

    object Order {
        private var autoOrder: Int = 0
            get() {
                field += 1
                return field
            }

        val DND_ENABLED = autoOrder
        val DND_ANDROID_SETTINGS = autoOrder
        val NOTIFICATION_ANDROID_APP_SETTINGS = autoOrder
        val NOTIFICATION_SHOW_TIME_ENABLED = autoOrder
        val SOUND_ENABLED = autoOrder
        val VIBRATION_ENABLED = autoOrder
        val AWAKE_KEEP_AWAKE = autoOrder
        val STATS_DEV_GENERATE_HISTORY = autoOrder
        val STATS_REMOVE_HISTORY = autoOrder
        val APP_VERSION = autoOrder
    }

    enum class Category {
        DND,
        NOTIFICATIONS,
        STATS,
        OTHERS,
        ABOUT,
    }
}