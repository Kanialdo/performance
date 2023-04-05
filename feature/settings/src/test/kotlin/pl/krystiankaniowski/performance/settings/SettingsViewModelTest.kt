package pl.krystiankaniowski.performance.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class SettingsViewModelTest {

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val providers = setOf(
            createEmptyProvider(),
        )

        val viewModel = SettingsViewModel(
            providers = providers,
        )

        Assertions.assertEquals(
            viewModel.state.value,
            SettingsViewModel.State.Loading,
        )
    }

    @Test
    fun `WHEN all providers provided data THEN loaded state is emitted`() = runTest {
        val item1 = SettingsItem.Simple(
            order = 1,
            category = SettingsItems.Category.ABOUT,
            title = "",
            description = null,
        )
        val item2 = SettingsItem.Simple(
            order = 2,
            category = SettingsItems.Category.ABOUT,
            title = "",
            description = null,
        )

        val providers = setOf(
            createSimpleProvider(item2),
            createSimpleProvider(item1),
        )

        val viewModel = SettingsViewModel(
            providers = providers,
        )

        Assertions.assertEquals(
            viewModel.state.value,
            SettingsViewModel.State.Loaded(
                items = mapOf(SettingsItems.Category.ABOUT to listOf(item1, item2)),
            ),
        )
    }

    private fun createEmptyProvider(): SettingsItemsProvider = object : SettingsItemsProvider {
        override val items: Flow<List<SettingsItem>> = emptyFlow()
    }

    private fun createSimpleProvider(value: SettingsItem): SettingsItemsProvider = object : SettingsItemsProvider {
        override val items: Flow<List<SettingsItem>> = flowOf(listOf(value))
    }
}