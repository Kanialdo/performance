package pl.krystiankaniowski.performance.account.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.account.R
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.stats.RemoveAllDataUseCase
import javax.inject.Inject

class AccountSettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val removeAllDataUseCase: RemoveAllDataUseCase,
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
                    buildRemoveAllDataItem(),
                ),
            )
        }
    }

    private fun buildRemoveAllDataItem() = SettingsItem.Simple(
        order = SettingsItems.Order.STATS_REMOVE_HISTORY,
        category = SettingsItems.Category.STATS,
        title = stringsProvider.getString(R.string.settings_remove_history_title),
        onClick = ::removeAllData,
    )

    private fun removeAllData() = scope.launch {
        removeAllDataUseCase()
    }
}