package pl.krystiankaniowski.performance.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsItemsProvider {

    val items: Flow<List<SettingsItem>>
}