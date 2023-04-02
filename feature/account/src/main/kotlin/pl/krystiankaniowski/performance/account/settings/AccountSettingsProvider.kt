package pl.krystiankaniowski.performance.account.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.account.R
import pl.krystiankaniowski.performance.domain.RemoveAllDataUseCase
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsOrder
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
        order = SettingsOrder.REMOVE_ALL_DATA,
        category = SettingsOrder.Category.STATS,
        title = stringsProvider.getString(R.string.title_remove_all_data),
        onClick = ::removeAllData,
    )

    private fun removeAllData() = scope.launch {
        removeAllDataUseCase()
    }
}