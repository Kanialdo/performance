package pl.krystiankaniowski.performance.dnd.settings

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.dnd.usecase.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class DndSettingsProviderTest {

    private val navigator: Navigator = mockk()
    private val stringsProvider: StringsProvider = mockk()
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase = mockk()
    private val setDoNotDisturbEnabledUseCase: SetDoNotDisturbEnabledUseCase = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        coEvery { stringsProvider.getString(any()) }.returns("")
        coEvery { isDoNotDisturbEnabledUseCase.invoke() }.returns(true)

        val sut = DndSettingsProvider(
            navigator = navigator,
            stringsProvider = stringsProvider,
            isDoNotDisturbEnabledUseCase = isDoNotDisturbEnabledUseCase,
            setDoNotDisturbEnabledUseCase = setDoNotDisturbEnabledUseCase,
        )

        with(sut.items.value.first() as SettingsItem.Switch) {
            Assertions.assertEquals(value, isDoNotDisturbEnabledUseCase())
        }
    }
}