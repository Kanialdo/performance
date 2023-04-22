package pl.krystiankaniowski.performance.history.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.history.usecase.GenerateHistoryUseCase
import pl.krystiankaniowski.performance.history.R
import javax.inject.Inject

class DebugHistorySettingsProvider @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val generateHistoryUseCase: GenerateHistoryUseCase,
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
                    buildGenerateStatsItem(),
                ),
            )
        }
    }

    private fun buildGenerateStatsItem() = SettingsItem.Simple(
        order = SettingsItems.Order.STATS_DEV_GENERATE_HISTORY,
        category = SettingsItems.Category.STATS,
        title = stringsProvider.getString(R.string.settings_generate_history_item),
        description = null,
        onClick = ::generateStats,
    )

    private fun generateStats() {
        scope.launch { generateHistoryUseCase() }
    }
}