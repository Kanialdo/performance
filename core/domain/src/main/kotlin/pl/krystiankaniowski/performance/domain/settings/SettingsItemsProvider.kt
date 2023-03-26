package pl.krystiankaniowski.performance.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsItemsProvider {

    val items: Flow<List<SettingsItem>>
}

sealed interface SettingsItem {

    val order: Int
    val title: String
    val description: String?

    data class Simple(
        override val order: Int,
        override val title: String,
        override val description: String? = null,
    ) : SettingsItem

    data class Switch(
        override val order: Int,
        override val title: String,
        override val description: String? = null,
        val value: Boolean,
        val isEnabled: Boolean,
        val onValueChanged: (Boolean) -> Unit,
    ) : SettingsItem
}