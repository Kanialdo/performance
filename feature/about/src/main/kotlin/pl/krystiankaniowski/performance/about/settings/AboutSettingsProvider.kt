package pl.krystiankaniowski.performance.about.settings

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.about.R
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import javax.inject.Inject
import javax.inject.Named

class AboutSettingsProvider @Inject constructor(
    @Named("appVersion") private val applicationVersion: String,
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
                    buildVersionItem(),
                ),
            )
        }
    }

    private fun buildVersionItem() = SettingsItem.Simple(
        order = SettingsItems.Order.APP_VERSION,
        category = SettingsItems.Category.ABOUT,
        title = stringsProvider.getString(R.string.title_app_version),
        description = applicationVersion,
    )
}